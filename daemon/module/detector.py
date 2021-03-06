# 공격여부 탐지 모듈

# input format: GET /uri... 
def is_sql_injection(method, uri):
    if not method: return False
    if not uri: return False
    if method != "GET": return False

    rules = ["\'", "%27", "%20", "UNION", "+"]

    uri = uri.split("?")
    if len(uri) == 1: return False
    param = uri[1]

    for rule in rules:
        if rule in param:
            return True
    
    return False

# input format: GET /uri... 
def is_rfi(method, uri):
    if not method: return False
    if not uri: return False
    if method != "GET": return False

    rules = [".", "%00", "/", "http"]

    uri = uri.split("?")
    if len(uri) == 1: return False
    param = uri[1]

    for rule in rules:
        if rule in param: return True

    return False

# input format: previous log byte count, current log byte count
## 공격 명령어 실행함수 탐지 방식으로 변경
def is_wshell(uri):
    if not uri: return False

    rules = ["system", "passthru", "shell_exec", "popen", "proc_open"] # execute command funcs 
    for rule in rules:
        if rule in uri : return True

    return False


def is_xss(method, uri):
    if not method: return False
    if not uri: return False
    if method != "GET": return False

    rules = ["script", "<", ">", "&lt;", "&gt;", "eval", "alert"]
    for rule in rules:
        if rule in uri : return True

    return False