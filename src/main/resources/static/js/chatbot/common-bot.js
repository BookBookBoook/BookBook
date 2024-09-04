var client;
let key;
let flag = false; // 챗봇이 열려있는 상태를 추적하는 플래그
let websocketStatus = 0; // 0: 서비스 안내, 1: 줄거리 검색

// WebSocket 지원 여부를 출력
function isWebSocketSupported() {
    return 'WebSocket' in window;
}

if (isWebSocketSupported()) {
    console.log("이 브라우저는 WebSocket을 지원합니다.");
} else {
    console.log("이 브라우저는 WebSocket을 지원하지 않습니다.");
}

// 시간 및 날짜 포맷 함수
function formatTime(now) {
    var ampm = (now.getHours() > 11) ? "오후" : "오전";
    var hour = now.getHours() % 12;
    if (hour == 0) hour = 12;
    var minute = now.getMinutes();
    var formattedMinute = String(minute).padStart(2, '0');
    return `${ampm} ${hour}:${formattedMinute}`;
}

function formatDate(now) {
    const year = now.getFullYear();
    const month = now.getMonth() + 1;
    const date = now.getDate();
    const dayOfWeek = now.getDay();
    const days = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    return `${year}년 ${month}월 ${date}일 ${days[dayOfWeek]}`;
}

// 메시지 표시 및 날짜 표시
function showMessage(tag) {
    var chatContent = document.getElementById("chat-content");
    chatContent.innerHTML += tag;
    chatContent.scrollTop = chatContent.scrollHeight;
}

// 날짜가 오늘인 경우, 로컬 스토리지에 저장된 날짜와 비교하여 중복 표시 방지
function showDateIfNew() {
    var now = new Date();
    var today = formatDate(now);
    
    var savedDate = localStorage.getItem('lastDisplayedDate');
    
    if (savedDate !== today) {
        var dateTag = `<div class="flex center date">${today}</div>`;
        var chatContent = document.getElementById("chat-content");
        chatContent.innerHTML = dateTag + chatContent.innerHTML;
        localStorage.setItem('lastDisplayedDate', today);
    }
}

// 환영 메시지 표시 및 저장
function showWelcomeMessage(botName, welcomeText) {
    const now = new Date();
    const today = formatDate(now);

    const welcomeMessage = `<div class="msg bot flex">
                                <div class="icon">
                                    <img src="/img/bot/bot-img.png">
                                </div>
                                <div class="message">
                                <div class="bot-name">${botName}</div>
                                    <div class="part chatbot">
                                        <p>${welcomeText}</p>
                                    </div>
                                </div>
                            </div>`;
    
    var hasShownWelcomeMessage = localStorage.getItem('hasShownWelcomeMessage');
    
    if (!hasShownWelcomeMessage) {
        showMessage(welcomeMessage);
        localStorage.setItem('hasShownWelcomeMessage', 'true');
    }

    localStorage.setItem('lastOpenedDate', today);
    showDateIfNew();
}

// WebSocket 연결 및 처리
function connect() {
    client = Stomp.over(new SockJS('/bookBot'));
    client.connect({}, (frame) => {
        console.log("Connected to WebSocket server with frame:", frame);
        
        key = new Date().getTime();
        client.subscribe(`/topic/bot/${key}`, (answer) => {
            console.log("응답완료!!!");
            var msgObj = answer.body;
            console.log("Received message from server:", msgObj);
            
            var now = new Date();
            var time = formatTime(now);
            
            if (websocketStatus === 0) {
                handleServiceMode(msgObj, time);
            } else if (websocketStatus === 1) {
                handleSearchMode(msgObj, time);
            }
        });
    });
}

// 서비스 모드에서의 처리 로직
function handleServiceMode(msgObj, time) {
    var tag = `<div class="msg bot flex">
                <div class="icon">
                    <img src="/img/bot/bot-img.png">
                </div>
                <div class="message">
                <div class="bot-name">북엉이</div>
                    <div class="part chatbot">
                        <p>${msgObj}</p>
                    </div>
                </div>
            </div>`;
    showMessage(tag);
    
    // 특정 메시지에 대해 버튼 추가
    if (msgObj.includes("배송조회")) {
        var buttonHTML = `<div class="msg bot flex">
                            <div class="icon">
                                <img src="/img/bot/bot-img-none.png">
                            </div>
                            <div class="message">
                                <div class="part chatbot">
                                    <p>아래 버튼을 통해 배송 조회 페이지로 이동해주세요!</p>
                                    <div class="button-container">
                                        <button class="faq-button" onclick="location.href='/mypage/orders';">배송조회 이동</button>
                                    </div>
                                </div>
                                <div class="time">${time}</div>
                            </div>
                        </div>`;
        showMessage(buttonHTML);
    }
    // 여기에 다른 서비스 모드의 처리 로직 추가
}

// 검색 모드에서의 처리 로직
function handleSearchMode(msgObj, time) {
    var tag = `<div class="msg bot flex">
                <div class="icon">
                    <img src="/img/bot/bot-img.png">
                </div>
                <div class="message">
                <div class="bot-name">검색봇</div>
                    <div class="part chatbot">
                        <p>${msgObj}</p>
                    </div>
                </div>
            </div>`;
    showMessage(tag);
    // 여기에 검색 모드의 처리 로직 추가
}

function disconnect() {
    if (client) {
        client.disconnect(() => {
            console.log("Disconnected...");
        });
    }
}

function saveBotState() {
    var isVisible = document.getElementById("bot-container").classList.contains('open');
    localStorage.setItem('botState', isVisible ? 'open' : 'closed');
}

function loadBotState() {
    var botState = localStorage.getItem('botState');
    const botContainer = document.getElementById("bot-container");

    if (botState === 'open') {
        botContainer.classList.add('open');
        flag = true;
        connect();
    } else {
        botContainer.classList.remove('open');
        flag = false;
        disconnect();
    }

    var hasShownWelcomeMessage = localStorage.getItem('hasShownWelcomeMessage');
    var wasChatReset = localStorage.getItem('chatReset');
    
    if (!hasShownWelcomeMessage || wasChatReset) {
        if (botState === 'open') {
            var botName = websocketStatus === 0 ? "북엉이" : "검색봇";
            var welcomeText = websocketStatus === 0 
                ? "안녕하세요. 안내봇 북엉이입니다. 무엇을 도와드릴까요?" 
                : "안녕하세요. 검색봇입니다. 원하는 책의 정보를 검색해드릴게요.";
                
            showWelcomeMessage(botName, welcomeText);
            localStorage.removeItem('chatReset');
        }
    }
}

window.addEventListener('beforeunload', function() {
    saveBotState();
    localStorage.removeItem('chatContent');
});

function btnCloseClicked() {
    const botContainer = document.getElementById("bot-container");
    botContainer.classList.remove('open');
    saveBotState();
    disconnect();
    flag = false;
    document.getElementById("chat-content").innerHTML = "";
    localStorage.removeItem('chatContent');
    localStorage.setItem('chatReset', 'true');
    localStorage.removeItem('hasShownWelcomeMessage');
}

function btnBotClicked() {
    if (flag) return;

    const botContainer = document.getElementById("bot-container");
    botContainer.classList.add('open');
    connect();
    flag = true;
    
    var hasShownWelcomeMessage = localStorage.getItem('hasShownWelcomeMessage');
    var wasChatReset = localStorage.getItem('chatReset');
    
    if (!hasShownWelcomeMessage || wasChatReset) {
        var botName = websocketStatus === 0 ? "북엉이" : "검색봇";
        var welcomeText = websocketStatus === 0 
            ? "안녕하세요. 안내봇 북엉이입니다. 무엇을 도와드릴까요?" 
            : "안녕하세요. 검색봇입니다. 원하는 책의 정보를 검색해드릴게요.";
        
        showWelcomeMessage(botName, welcomeText);
        localStorage.removeItem('chatReset');
    }
    
    saveBotState();
}

function btnMsgSendClicked() {
    if (!client) {
        console.error("WebSocket client is not initialized.");
        return;
    }

    var question = document.getElementById("question").value.trim();
    if (question.length < 2) {
        alert("질문은 최소 2글자 이상으로 부탁드립니다.");
        return;
    }

    var now = new Date();
    var time = formatTime(now);
    var tag = `<div class="msg user flex">
                <div class="message">
                    <div class="part guest">
                        <p>${question}</p>
                    </div>
                    <div class="time">${time}</div>
                </div>
            </div>`;

    showDateIfNew();
    showMessage(tag);

    var data = {
        key: key,
        content: question,
        userId: 1
    };

    // 웹소켓 상태에 따라 다른 경로로 메시지 전송
    if (websocketStatus === 0) {
        client.send(`/message/bot/question`, {}, JSON.stringify(data));
    } else if (websocketStatus === 1) {
        client.send(`/message/bot/openai`, {}, JSON.stringify(data));
    }
    
    clearQuestion();
}

function clearQuestion() {
    document.getElementById("question").value = "";
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', (event) => {
    btnCloseClicked();
    loadBotState();

    document.getElementById("chat-icon").addEventListener('click', btnBotClicked);
    document.getElementById("close-button").addEventListener('click', btnCloseClicked);
    document.getElementById("send-button").addEventListener('click', btnMsgSendClicked);

    document.getElementById("question").addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            btnMsgSendClicked();
        }
    });

    // 체크박스 클릭 시 모드 전환
    document.getElementById("mode-switch").addEventListener('change', function() {
        websocketStatus = websocketStatus === 0 ? 1 : 0;
        toggleMode(websocketStatus);
        saveBotState();
    });
});

function toggleMode(status) {
    var serviceInfo = document.getElementById("service-info");
    var searchInfo = document.getElementById("search-info");
    
    if (status === 0) {
        serviceInfo.style.display = "block";
        searchInfo.style.display = "none";
    } else {
        serviceInfo.style.display = "none";
        searchInfo.style.display = "block";
    }

    // 채팅 내용 초기화 및 새 모드의 환영 메시지 표시
    document.getElementById("chat-content").innerHTML = "";
    localStorage.removeItem('chatContent');
    localStorage.removeItem('hasShownWelcomeMessage');
    
    if (flag) {
        var botName = status === 0 ? "북엉이" : "검색봇";
        var welcomeText = status === 0 
            ? "안녕하세요. 안내봇 북엉이입니다. 무엇을 도와드릴까요?" 
            : "안녕하세요. 검색봇입니다. 원하는 책의 정보를 검색해드릴게요.";
        
        showWelcomeMessage(botName, welcomeText);
    }
}
