import logging

from fastapi import Request
from fastapi.responses import JSONResponse

logger = logging.getLogger("ai-backend")

async def handle_unexpected(request: Request, exc: Exception) -> JSONResponse:
    """모든 미처리 예외를 표준 500 응답으로 변환합니다."""
    logger.exception("처리되지 않은 오류: %s", request.url.path)
    return JSONResponse(
        status_code=500,
        content={
            "error": "internal_server_error",
            "detail": str(exc),
            "path": request.url.path,
        }
    )