#!/usr/bin/env bash
set -euo pipefail

SOURCE_REPO="kaiqkt/kt-template-api"
TARGET_REPO="${GITHUB_REPOSITORY}"

echo "Source repository: $SOURCE_REPO"
echo "Target repository: $TARGET_REPO"
echo

RULESETS=$(gh api \
  -H "Accept: application/vnd.github+json" \
  /repos/$SOURCE_REPO/rulesets)

COUNT=$(echo "$RULESETS" | jq 'length')

if [ "$COUNT" -eq 0 ]; then
  echo "No rulesets found in source repository"
  exit 0
fi

echo "Found $COUNT rulesets"
echo

echo "$RULESETS" | jq -c '.[]' | while read -r RULESET; do
  NAME=$(echo "$RULESET" | jq -r '.name')
  ID=$(echo "$RULESET" | jq -r '.id')

  echo "➡️ Copying ruleset: $NAME (id=$ID)"

  CLEANED=$(echo "$RULESET" | jq 'del(
    .id,
    .node_id,
    .source,
    .source_type,
    .created_at,
    .updated_at,
    ._links
  )')

  gh api \
    --method POST \
    -H "Accept: application/vnd.github+json" \
    /repos/$TARGET_REPO/rulesets \
    --input <(echo "$CLEANED")

  echo "✅ Ruleset '$NAME' created"
  echo
done

echo "🎉 All rulesets copied successfully"
