#!/usr/bin/env bash
# Runs ApacheBench load tests against /normal and /fast endpoints

set -euo pipefail

HOST=${1:-"http://127.0.0.1:8080"}
PAYLOAD="post.json"
CONCURRENCY=100
REQUESTS=5000

echo "=== Benchmarking /normal (Jackson baseline) ==="
ab -n "$REQUESTS" -c "$CONCURRENCY" -p "$PAYLOAD" -T "application/json" "$HOST/normal"

echo ""
echo "=== Benchmarking /fast (Zero-allocation pipeline) ==="
ab -n "$REQUESTS" -c "$CONCURRENCY" -p "$PAYLOAD" -T "application/json" "$HOST/fast"
