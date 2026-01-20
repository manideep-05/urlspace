import SockJS from "sockjs-client";
import Stomp from "stompjs";

let stompClient = null;

export const connectAlertSocket = (onAlert) => {
  const socket = new SockJS("http://localhost:9090/ws-alerts");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    stompClient.subscribe("/topic/alerts", (message) => {
      const alert = JSON.parse(message.body);
      onAlert(alert);
    });
  });
};

export const disconnectAlertSocket = () => {
  if (stompClient) {
    stompClient.disconnect();
  }
};
