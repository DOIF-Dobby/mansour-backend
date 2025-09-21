# 추가
curl -X POST http://localhost:18083/connectors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "activity-outbox-connector",
    "config": {
      "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
      "database.hostname": "postgres",
      "database.port": "5432",
      "database.user": "postgres",
      "database.password": "postgres",
      "database.dbname": "activity",
      "database.server.name": "activity",
      "topic.prefix": "activity",
      "table.include.list": "public.outbox",
      "slot.name": "debezium_slot",
      "plugin.name": "pgoutput",
      "tombstones.on.delete": "false",
      "transforms": "outbox",
      "transforms.outbox.type": "io.debezium.transforms.outbox.EventRouter",
      "transforms.outbox.table.field.event.key": "id"
    }
  }'

# 확인용
curl http://localhost:18083/connectors/activity-outbox-connector/status

# 삭제
curl -X DELETE http://localhost:18083/connectors/activity-outbox-connector

# 카프카 확인용
docker exec mansour-kafka kafka-topics --bootstrap-server localhost:9092 --list
docker exec -it mansour-kafka kafka-console-consumer \                                                                                                                                                                                   INT ✘  19:47:37 
  --bootstrap-server localhost:9092 \
  --topic outbox.event.InterestAsset \
  --from-beginning \
  --property print.key=true \
  --property key.separator=" : "
