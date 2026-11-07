"""Exercise the real MySQL-backed API; then verify persistence after restart."""
import json
import sys
import time
import urllib.error
import urllib.request
from pathlib import Path

BASE = "http://localhost:8080/api/v1/courses"

def request(method, url, value=None):
    data = None if value is None else json.dumps(value).encode()
    req = urllib.request.Request(url, data=data, method=method,
                                 headers={"Content-Type": "application/json"})
    try:
        response = urllib.request.urlopen(req, timeout=5)
    except urllib.error.HTTPError as error:
        response = error
    with response:
        raw = response.read().decode()
        return response.status, dict(response.headers), json.loads(raw) if raw else None

def expect(method, url, status, value=None):
    actual, headers, body = request(method, url, value)
    assert actual == status, (method, url, actual, status, body)
    return headers, body

for attempt in range(60):
    try:
        status, _, _ = request("GET", BASE)
        if status == 200:
            break
    except (OSError, TimeoutError):
        pass
    time.sleep(1)
else:
    raise AssertionError("API did not start within 60 seconds")

if sys.argv[1] == "create":
    course = {"code": "SE411", "name": "MySQL Check", "description": "Integration test", "credits": 3}
    headers, body = expect("POST", BASE, 201, course)
    location = next(value for key, value in headers.items() if key.lower() == "location")
    assert location.endswith("/api/v1/courses/" + str(body["id"]))
    _, found = expect("GET", location, 200)
    assert found["name"] == course["name"]
    course["name"] = "Updated MySQL Check"
    expect("PUT", location, 204, course)
    _, found = expect("GET", location, 200)
    assert found["name"] == course["name"]
    expect("DELETE", location, 204)
    expect("GET", location, 404)
    expect("DELETE", location, 404)
    expect("PUT", location, 404, course)
    expect("GET", BASE + "/-1", 400)
    expect("POST", BASE, 400, dict(course, credits=11))
    duplicate = dict(course, id=900)
    expect("POST", BASE, 201, duplicate)
    expect("POST", BASE, 422, duplicate)
    expect("DELETE", BASE + "/900", 204)
    _, persistent = expect("POST", BASE, 201, dict(course, code="PERSIST"))
    Path("persistence-id.txt").write_text(str(persistent["id"]))
    print("PASS: real MySQL CRUD, Location, invalid request, duplicate id, missing resources")
else:
    url = BASE + "/" + Path("persistence-id.txt").read_text()
    _, persistent = expect("GET", url, 200)
    assert persistent["code"] == "PERSIST"
    expect("DELETE", url, 204)
    print("PASS: record survived application restart")
