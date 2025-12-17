```bash
curl -X PUT -d '{
  "orgLat":42.680835452638945,
  "orgLng":-88.28317875190854,
  "dstLat":43.03205698379127,
  "dstLng":-87.91330479781325
}' -H 'Content-Type: application/json' localhost:8080/pathfinding
```