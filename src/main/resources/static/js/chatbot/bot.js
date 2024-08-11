var client;
var key;
let flag = false;
let lastDate = null; // 마지막으로 표시된 날짜

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
    const month = now.getMonth() + 1; // 월 정보는 0월부터 시작하기 때문에 +1 해줘야 함
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
    
    // 로컬 스토리지에서 저장된 마지막 날짜 가져오기
    var savedDate = localStorage.getItem('lastDisplayedDate');
    
    if (savedDate !== today) {
        var dateTag = `<div class="flex center date">${today}</div>`;
        var chatContent = document.getElementById("chat-content");
        chatContent.innerHTML = dateTag + chatContent.innerHTML;
        localStorage.setItem('lastDisplayedDate', today); // 오늘 날짜를 로컬 스토리지에 저장
    }
}

// 환영 메시지 표시 및 저장
function showWelcomeMessage() {
    const now = new Date();
    const today = formatDate(now);

    const welcomeMessage = `<div class="msg bot flex">
                                <div class="icon">
                                    <img src="/img/bot/bot-img.png">
                                </div>
                                <div class="message">
								<div class="bot-name">북Book</div>
                                    <div class="part chatbot">
                                        <p>
                                        	안녕하세요. <br> 
											안내봇 ㅇㅇㅇ입니다. <br>
                                        	무엇을 도와드릴까요?
                                    	</p>
                                    </div>
                                </div>
                            </div>`;
    showMessage(welcomeMessage);
    localStorage.setItem('lastOpenedDate', today);
    showDateIfNew();
}

// WebSocket 연결 및 처리
function connect() {
    client = Stomp.over(new SockJS('/bookBot'));
    client.connect({}, (frame) => {
		// 연결 성공 시 frame 객체의 정보를 콘솔에 출력
        console.log("Connected to WebSocket server with frame:", frame);
        
        key = new Date().getTime();
        client.subscribe(`/topic/bot/${key}`, (answer) => {
            var msgObj = answer.body;
            console.log("Received message from server:", msgObj);

            var now = new Date();
            var time = formatTime(now);
            var tag = `<div class="msg bot flex">
                        <div class="icon">
                            <img src="/img/bot/bot-img.png">
                        </div>
                        <div class="message">
                            <div class="part chatbot">
                                <p>${msgObj}</p>
                            </div>
                            <div class="time">${time}</div>
                        </div>
                    </div>`;
            showMessage(tag);
        });

        
    });
}

// WebSocket 연결 종료
function disconnect() {
    if (client) {
        client.disconnect(() => {
            console.log("Disconnected...");
        });
    }
}

// 상태 저장 및 복원
function saveChatContent() {
    var chatContent = document.getElementById("chat-content").innerHTML;
    localStorage.setItem('chatContent', chatContent);
}

function loadChatContent() {
    var savedContent = localStorage.getItem('chatContent');
    if (savedContent) {
        document.getElementById("chat-content").innerHTML = savedContent;
    }
}

function saveBotState() {
    var isVisible = document.getElementById("bot-container").style.display === 'block';
    localStorage.setItem('botState', isVisible ? 'open' : 'closed');
}

function loadBotState() {
    var botState = localStorage.getItem('botState');
    if (botState === 'open') {
        document.getElementById("bot-container").style.display = 'block';
        flag = true;
        connect();
        showWelcomeMessage();
    } else {
        document.getElementById("bot-container").style.display = 'none';
        flag = false;
        disconnect();
    }
}

// 버튼 클릭 이벤트 핸들러
function btnCloseClicked() {
    document.getElementById("bot-container").style.display = 'none';
    saveBotState();
    disconnect();
    flag = false;
	document.getElementById("chat-content").innerHTML = ""; // 채팅 내용 초기화
	localStorage.removeItem('chatContent'); // 로컬 스토리지에서 채팅 내용 제거

}

function btnBotClicked() {
    if (flag) return;
    document.getElementById("bot-container").style.display = 'block';
    connect();
    flag = true;
    showWelcomeMessage();
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
    saveChatContent();

    var data = {
        key: key,
        content: question,
        userId: 1
    };
    client.send("/message/bot/question", {}, JSON.stringify(data));
    clearQuestion();
}

function clearQuestion() {
    document.getElementById("question").value = "";
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', (event) => {
    loadChatContent();
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
});
