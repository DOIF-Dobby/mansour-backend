#!/bin/sh

echo "Waiting for Kafka Connect to be ready..."

# Kafka Connect가 준비될 때까지 대기
while ! curl -sf http://connect:8083/connectors; do
  echo "Waiting for Kafka Connect..."
  sleep 5
done

echo "Kafka Connect is ready. Creating connectors..."

# connector-configs 디렉토리의 모든 JSON 파일 처리
for config_file in /connector-configs/*.json; do
  if [ -f "$config_file" ]; then
    echo "Processing: $config_file"

    # JSON 파일에서 커넥터 이름 추출 (jq가 없으므로 grep과 sed 사용)
    connector_name=$(grep '"name"' "$config_file" | head -1 | sed 's/.*"name"[[:space:]]*:[[:space:]]*"\([^"]*\)".*/\1/')

    if [ -n "$connector_name" ]; then
      echo "Found connector: $connector_name"

      # 기존 커넥터가 있으면 삭제
      echo "Deleting existing connector if exists..."
      curl -X DELETE "http://connect:8083/connectors/$connector_name" 2>/dev/null || true

      # 잠시 대기
      sleep 2

      # 커넥터 생성
      echo "Creating connector: $connector_name"
      response=$(curl -s -w "%{http_code}" -X POST http://connect:8083/connectors \
        -H "Content-Type: application/json" \
        -d @"$config_file")

      http_code="${response: -3}"
      response_body="${response%???}"

      if [ "$http_code" = "201" ] || [ "$http_code" = "200" ]; then
        echo "✓ Connector '$connector_name' created successfully!"
      else
        echo "✗ Failed to create connector '$connector_name' (HTTP $http_code)"
        echo "Response: $response_body"
      fi
    else
      echo "✗ Could not extract connector name from $config_file"
    fi

    echo "---"
  fi
done

echo "Connector setup completed!"
