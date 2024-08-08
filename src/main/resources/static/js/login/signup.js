function validateForm() {
    const id = document.getElementById("signup-id").value.trim();
    const pw = document.getElementById("signup-pw").value.trim();
    const name = document.getElementById("signup-name").value.trim();
    const birth = document.getElementById("signup-birth").value.trim();
    const gender = document.getElementById("signup-gender").value;
    const ssn = document.getElementById("signup-ssn").value.trim();
    const email = document.getElementById("signup-email").value.trim();
    const phone = document.getElementById("signup-phone").value.trim();
    const address = document.getElementById("sample6_address").value.trim();
    const detailAddress = document.getElementById("sample6_detailAddress").value.trim();

    let errorMessages = [];

    if (id === "") {
        errorMessages.push("아이디를 입력하세요.");
    }
    if (pw === "") {
        errorMessages.push("비밀번호를 입력하세요.");
    } else if (pw.length < 8) {
        errorMessages.push("비밀번호는 최소 8자 이상이어야 합니다.");
    }
    if (name === "") {
        errorMessages.push("이름을 입력하세요.");
    }
    if (!/^\d{4}-\d{2}-\d{2}$/.test(birth)) {
        errorMessages.push("생년월일 형식이 올바르지 않습니다. (YYYY-MM-DD)");
    }
    if (gender === "") {
        errorMessages.push("성별을 선택하세요.");
    }
    if (!/^\d{6}-\d{7}$/.test(ssn)) {
        errorMessages.push("주민등록번호 형식이 올바르지 않습니다. (123456-1234567)");
    }
    if (email !== "" && !/.+@.+\..+/.test(email)) {
        errorMessages.push("이메일 형식이 올바르지 않습니다.");
    }
    if (!/01[0-9]-[0-9]{4}-[0-9]{4}/.test(phone)) {
        errorMessages.push("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)");
    }
    if (address === "") {
        errorMessages.push("주소를 입력하세요.");
    }
    if (detailAddress === "") {
        errorMessages.push("상세주소를 입력하세요.");
    }

    if (errorMessages.length > 0) {
        alert(errorMessages.join("\n"));
    } else {
        alert("가입이 완료되었습니다.");
        // 실제 가입 로직을 여기에 추가
    }
}
