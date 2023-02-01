
var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var chatRoomId = document.querySelector('#chat-page')

var stompClient = null;
var username = null;
var conversationId = null;

function connect(event) {
    username = document.querySelector('#name').value.trim();
    conversationId = document.querySelector('#conversationId').value.trim();

    usernamePage.classList.add('hidden');
    document.getElementById("tit").innerHTML = "Username: " + username + ", ConversationId: " + conversationId;
    chatPage.classList.remove('hidden');

    var socket = new SockJS('/stomp/chat');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);

    event.preventDefault();
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/sub/' + chatRoomId, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/pub/chat/message",
        {},
        JSON.stringify({username: username, type: 'JOIN'})  //stringify jsom문자열로 변환
    )

    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            conversationId: conversationId,
            username: username,
            content: messageInput.value
            //type: 'TEXT'
        };

        stompClient.send("/pub/chat/message", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    //message.

    var messageElement = document.createElement('li');


    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.username + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.username + ' left!';
    } else {
        if (message.conversationId != conversationId)
            return;
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.username[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.username);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.username);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;

}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)

