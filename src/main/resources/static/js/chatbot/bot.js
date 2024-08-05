var client;
var key;
let flag = false;

// 브라우저가 WebSocket을 지원하는지 확인하는 함수
function isWebSocketSupported() {
    return 'WebSocket' in window;
}

// WebSocket 지원 여부를 출력
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

// 대화 내용 추가
function showMessage(tag) {
    document.getElementById("chat-content").innerHTML += tag;
    // 스크롤을 제일 아래로
    document.getElementById("chat-content").scrollTop = document.getElementById("chat-content").scrollHeight;
}

// 웹소켓 연결 후 인사말 출력
function connect() {
    client = Stomp.over(new SockJS('/ws-green-bot'));
    client.connect({}, (frame) => {
        key = new Date().getTime();
        client.subscribe(`/topic/bot/${key}`, (answer) => {
            var msgObj = answer.body;
            var now = new Date();
            var time = formatTime(now);
            var date = formatDate(now);
            var tag = `<div class="flex center date">${date}</div>
                        <div class="msg bot flex">
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

// 웹소켓 종료
function disconnect() {
    client.disconnect(() => {
        console.log("Disconnected...");
    });
}

// 종료(X) 버튼 클릭 시 이벤트
function btnCloseClicked() {
    document.getElementById("bot-container").style.display = 'none';
    // 대화창 리셋
    document.getElementById("chat-content").innerHTML = "";
    disconnect();
    flag = false;
}

// 챗봇 시작 시 버튼 이벤트
function btnBotClicked() {
    if (flag) return; // 챗봇을 한 번만 켜주기 위해서
    document.getElementById("bot-container").style.display = 'block';
    connect();
    flag = true;
}

function clearQuestion() {
    document.getElementById("question").value = ""; // setter처럼 사용
}

// 메시지 전송
// 사용자가 채팅 메시지를 입력했을 때
function btnMsgSendClicked() {
    var question = document.getElementById("question").value.trim();
    if (question.length < 2) {
        alert("질문은 최소 2글자 이상으로 부탁드립니다.");
        return;
    }
    var data = {
        key: key,
        content: question,
        name: "guest"
    };
    client.send("/bot/question", {}, JSON.stringify(data));
    clearQuestion();
}

// 초기화 및 이벤트 바인딩
document.getElementById("chat-icon").addEventListener('click', btnBotClicked);
document.getElementById("close-button").addEventListener('click', btnCloseClicked);
document.getElementById("send-button").addEventListener('click', btnMsgSendClicked);

// 페이지 로드 시 챗봇 아이콘 클릭 시 챗봇 열리기
document.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById("chat-icon").addEventListener('click', btnBotClicked);
});