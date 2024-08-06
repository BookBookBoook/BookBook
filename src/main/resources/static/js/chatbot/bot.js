var client;
var key;
let flag = false;
let lastDate = null; // 마지막으로 표시된 날짜
let lastOpenedDate = localStorage.getItem('lastOpenedDate'); // 로컬 스토리지에서 마지막 열었던 날짜 가져오기

// WebSocket 지원 여부를 출력
function isWebSocketSupported() {
    return 'WebSocket' in window;
}

if (isWebSocketSupported()) {
    console.log("이 브라우저는 WebSocket을 지원합니다.");
} else {
    console.log("이 브라우저는 WebSocket을 지원하지 않습니다.");
}

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

function showMessage(tag) {
    var chatContent = document.getElementById("chat-content");
    chatContent.innerHTML += tag;
    // 스크롤을 최하단으로 이동
    chatContent.scrollTop = chatContent.scrollHeight;
}

function showDateIfNew() {
    var now = new Date();
    var today = formatDate(now);
    if (lastDate !== today) {
        var dateTag = `<div class="flex center date">${today}</div>`;
        showMessage(dateTag);
        lastDate = today;
    }
}

function showWelcomeMessage() {
    const now = new Date();
    const today = formatDate(now);

    const welcomeMessage = `<div class="msg bot flex">
                                <div class="icon">
                                    <img src="/img/bot/bot-img.png">
                                </div>
                                <div class="message">
                                    <div class="part">
                                        <p>안녕하세요! 무엇을 도와드릴까요?</p>
                                    </div>
                                </div>
                            </div>`;
    showMessage(welcomeMessage);
    localStorage.setItem('lastOpenedDate', today); // 현재 날짜를 로컬 스토리지에 저장
}

function connect() {
    client = Stomp.over(new SockJS('/ws-green-bot'));
    client.connect({}, (frame) => {
        key = new Date().getTime();
        client.subscribe(`/topic/bot/${key}`, (answer) => {
            var msgObj = answer.body;
            var now = new Date();
            var time = formatTime(now);
            var tag = `<div class="msg bot flex">
                        <div class="icon">
                            <img src="/images/icon/robot-solid.svg">
                        </div>
                        <div class="message">
                            <div class="part">
                                <p>${msgObj}</p>
                            </div>
                            <div class="time">${time}</div>
                        </div>
                    </div>`;
            showDateIfNew();
            showMessage(tag);
        });

        var data = {
            key: key,
            content: "hello",
            name: "guest"
        };
        client.send("/bot/hello", {}, JSON.stringify(data));
    });
}

function disconnect() {
    if (client) {
        client.disconnect(() => {
            console.log("Disconnected...");
        });
    }
}

function btnCloseClicked() {
    document.getElementById("bot-container").style.display = 'none'; // 챗봇 창 숨기기
    document.getElementById("chat-content").innerHTML = ""; // 대화 내용 초기화
    disconnect(); // 웹소켓 연결 종료
    flag = false;
}

function btnBotClicked() {
    if (flag) return; // 이미 챗봇이 켜져 있다면 클릭 무시
    document.getElementById("bot-container").style.display = 'block'; // 챗봇 창 보이기
    connect(); // 웹소켓 연결
    flag = true;
    showWelcomeMessage(); // 챗봇이 열릴 때마다 안내문 표시
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
                    <div class="part">
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
        name: "guest"
    };
    client.send("/bot/question", {}, JSON.stringify(data));
    
    clearQuestion(); // 입력창 초기화
}

function clearQuestion() {
    document.getElementById("question").value = ""; // 입력창을 빈 문자열로 설정
}

document.addEventListener('DOMContentLoaded', (event) => {
    // 초기 상태 설정
    document.getElementById("bot-container").style.display = 'none';

    document.getElementById("chat-icon").addEventListener('click', btnBotClicked);
    document.getElementById("close-button").addEventListener('click', btnCloseClicked);
    document.getElementById("send-button").addEventListener('click', btnMsgSendClicked);
});
