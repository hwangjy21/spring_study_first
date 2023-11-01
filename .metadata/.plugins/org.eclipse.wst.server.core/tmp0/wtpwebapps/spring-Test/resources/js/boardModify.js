async function removeFile(uuid) {
    try {
        const url = '/board/file/' + uuid;
        const config = {
            method: 'DELETE'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (err) {
        console.log(err);
    }
}

document.addEventListener('click', (e) => {
    console.log(e.target);
    if (e.target.classList.contains('file-x')) {
        console.log('삭제버튼 클릭');
      
     
        let uuid= e.target.dataset.uuid; // 'detaset'이 아니라 'dataset'을 사용해야 함
        console.log('uuid');
        removeFile(uuid).then(result => {
            if (result == '1') { // 문자열 '1'로 비교
                alert('파일 삭제 성공~!!');
                e.target.closest('li').remove(); //li 삭제
                location.reload(); //새로고침
            } else if (result == '2') { // 문자열 '2'로 비교
                alert("파일 삭제 실패.");
            }
        })
    }
});
