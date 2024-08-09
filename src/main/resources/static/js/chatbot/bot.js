var client;
var key;
let flag = false;
let lastDate = null; // 마지막으로 표시된 날짜
let lastOpenedDate = localStorage.getItem('lastOpenedDate'); // 로컬 스토리지에서 마지막 열었던 날짜 가져오기

/* WebSocket 지원 여부를 출력 */
function isWebSocketSupported() {
    return 'WebSocket' in window;
}

if (isWebSocketSupported()) {
    console.log("이 브라우저는 WebSocket을 지원합니다.");
} else {
    console.log("이 브라우저는 WebSocket을 지원하지 않습니다.");
}
/*////////////////////////*/
/* 시간 및 날짜 포맷 함수 */
function formatTime(now) { //현재 시간 객체를 "오후/오전 시:분" 형식으로 포맷
    var ampm = (now.getHours() > 11) ? "오후" : "오전";
    var hour = now.getHours() % 12;
    if (hour == 0) hour = 12;
    var minute = now.getMinutes();
    var formattedMinute = String(minute).padStart(2, '0');
    return `${ampm} ${hour}:${formattedMinute}`;
}

function formatDate(now) { //현재 날짜 객체를 "YYYY년 MM월 DD일 요일" 형식으로 포맷
    const year = now.getFullYear();
    const month = now.getMonth() + 1; // 월 정보는 0월부터 시작하기 때문에 +1 해줘야 함
    const date = now.getDate();
    const dayOfWeek = now.getDay(); 
    const days = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
    return `${year}년 ${month}월 ${date}일 ${days[dayOfWeek]}`;
}
/*////////////////////////*/
/* 메시지 표시 및 날짜 표시 */
function showMessage(tag) { // chat-content 요소에 메시지를 추가 / 스크롤을 자동최하단 이동
    var chatContent = document.getElementById("chat-content");
    chatContent.innerHTML += tag;
    // 스크롤을 최하단으로 이동
    chatContent.scrollTop = chatContent.scrollHeight;
}

function showDateIfNew() { // 오늘 날짜가 마지막으로 표시된 날짜와 다르면 날짜를 표시
    var now = new Date();
    var today = formatDate(now);
    if (lastDate !== today) {
        var dateTag = `<div class="flex center date">${today}</div>`;
        showMessage(dateTag);
        lastDate = today;
    }
}
/*////////////////////////*/
/* 환영 메시지 표시 및 저장 */
function showWelcomeMessage() { // 챗봇이 열릴 때 환영 메시지를 표시/ 현재 날짜를 로컬 스토리지에 저장
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
/*/////////////////////////////////////////////////////////////////*/
/* /////////////////////////////////////////// WebSocket 연결 및 처리 */
function connect() { 
	// WebSocket을 통해 서버와 연결
	// 특정 채널(/topic/bot/${key})을 구독 = websocketController @SendTo
    client = Stomp.over(new SockJS('/bookBot'));
    client.connect({}, (frame) => {
        key = new Date().getTime();
        client.subscribe(`/topic/bot/${key}`, (answer) => {
            var msgObj = answer.body;
            
            // 콘솔에 분석 결과 출력
            console.log("Received message from server:", msgObj);

            // 시간 및 날짜 포맷
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
            content: "answer",
            name: "userId"
        };
        client.send("/message/bot/answer", {}, JSON.stringify(data)); 
        //웹소켓컨트롤러로 전송경로
    });
}
/*////////////////////////////////////////////////////////////////*/
/*////////////////////////////////////////////////////////////////*/
/* WebSocket 연결 종료 */
function disconnect() {
    if (client) {
        client.disconnect(() => {
            console.log("Disconnected...");
        });
    }
}
/*////////////////////////*/

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
/* //////////////////////////////////////////////// */
/* //////////////////////////////////////////////// */

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
        name: "userId" // 실제 시스템에서는 여기에 사용자의 고유 ID를 설정할 수 있음
		// 사용자정보 안받아올거여도 필요한지 알아보기
    };
    client.send("/message/bot/question", {}, JSON.stringify(data));
	// client.send(destination, headers, body) 
    // destination: 메시지가 전송될 대상 경로를 지정
	// headers: 메시지에 대한 추가 헤더 정보를 담는 객체 /ex). 메시지의 타입, 인증 정보
	// body: 전송할 메시지의 본문을 담고 있는 문자열
    clearQuestion(); // 입력창 초기화
}
/* //////////////////////////////////////////////// */
/* //////////////////////////////////////////////// */
function clearQuestion() {
    document.getElementById("question").value = ""; // 입력창을 빈 문자열로 설정
}

document.addEventListener('DOMContentLoaded', (event) => {
    // 초기 상태 설정
    document.getElementById("bot-container").style.display = 'none';

    document.getElementById("chat-icon").addEventListener('click', btnBotClicked);
    document.getElementById("close-button").addEventListener('click', btnCloseClicked);
    document.getElementById("send-button").addEventListener('click', btnMsgSendClicked);
    
    // Enter 키를 눌렀을 때 메시지 전송 버튼 클릭 이벤트 처리
    document.getElementById("question").addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault(); // 기본 Enter 키 동작 방지 (예: 폼 제출)
            btnMsgSendClicked(); // 메시지 전송 버튼 클릭 이벤트 호출
        }
    });
});
