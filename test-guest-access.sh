#!/bin/bash

BASE_URL="http://localhost:8080"
GUEST_USER="guest"
GUEST_PASS="visitor123"

echo "======== Testing Guest Access ========"
echo ""

# Test 1: Public endpoints (no auth required)
echo "1. Public Endpoints (no auth):"
echo "   Health: $(curl -s $BASE_URL/actuator/health | grep -o '"status":"[^"]*"')"
echo "   Prometheus: $(curl -s $BASE_URL/actuator/prometheus | head -1)"
echo ""

# Test 2: Guest read access (allowed)
echo "2. Guest READ access (allowed):"
echo "   Products: $(curl -s -u $GUEST_USER:$GUEST_PASS $BASE_URL/products | head -c 50)..."
echo "   Restaurants: $(curl -s -u $GUEST_USER:$GUEST_PASS $BASE_URL/restaurants | head -c 50)..."
echo ""

# Test 3: Guest write access (denied)
echo "3. Guest WRITE access (should be denied):"
curl -s -u $GUEST_USER:$GUEST_PASS -X POST $BASE_URL/products \
  -H "Content-Type: application/json" \
  -d '{"name":"test"}' | grep -o '"status":[0-9]*\|"message":"[^"]*"' || echo "   403 Forbidden (expected)"
echo ""

# Test 4: Customer access (denied)
echo "4. Customer access (should be denied):"
curl -s -u $GUEST_USER:$GUEST_PASS $BASE_URL/customers | grep -o '"status":[0-9]*' || echo "   403 Forbidden (expected)"
echo ""

# Test 5: Invalid credentials
echo "5. Invalid credentials (should be denied):"
curl -s -u invalid:wrong $BASE_URL/products | grep -o '"status":[0-9]*' || echo "   401 Unauthorized (expected)"
echo ""

echo "======== Test Complete ========"
