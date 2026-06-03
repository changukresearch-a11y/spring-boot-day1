from fastapi import APIRouter, Depends
from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI

from ..dependencies import Settings, get_llm, get_settings
from ..schemas import ChatRequest, ChatResponse

router = APIRouter(prefix="/chat", tags=["chat"])

@router.post("")
async def chat(req: ChatRequest,
    llm: ChatOpenAI = Depends(get_llm),
    settings: Settings = Depends(get_settings)
) -> ChatResponse:
    """LangChain ChatOpenAI 단일 체인 호출."""

    prompt = ChatPromptTemplate.from_messages(
        [("system", "당신은 친절하고 정확한 한국어 도우미입니다."), ("human", "{q}")]
    )
    chain = prompt | llm
    result = await chain.ainvoke({"q": req.prompt})
    return ChatResponse(answer=result.content, model=settings.model_name)