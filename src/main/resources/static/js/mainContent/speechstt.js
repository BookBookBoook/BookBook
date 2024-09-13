document.addEventListener('DOMContentLoaded', function() {
  const sttButton = document.getElementById('stt-button');
  if (sttButton) {
    sttButton.addEventListener('click', startSpeechRecognition);
  }
});

function startSpeechRecognition() {
  const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
  
  if (SpeechRecognition) {
    const recognition = new SpeechRecognition();
    recognition.lang = 'ko-KR';
    recognition.start();

    recognition.onresult = function(event) {
      const transcript = event.results[0][0].transcript;
      document.getElementById('search-query').value = transcript;
    };

    recognition.onerror = function(event) {
      console.error('Speech recognition error', event.error);
      alert('음성 인식 중 오류가 발생했습니다: ' + event.error);
    };

    recognition.onend = function() {
      console.log('음성 인식이 종료되었습니다.');
    };
  } else {
    alert('죄송합니다. 현재 브라우저에서는 음성 인식을 지원하지 않습니다. Chrome 브라우저 사용을 권장합니다.');
  }
}