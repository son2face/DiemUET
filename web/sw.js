/**
 * Created by Son on 7/2/2017.
 */
console.log("sw");

self.addEventListener('push', function(event) {
    console.log('Received a push message', event);
    console.log(event.data.text());
    var title = 'Yay a message.';
    var body = 'We have received a push message.';
    var icon = '/images/icon-192x192.png';
    var tag = 'simple-push-demo-notification-tag';
    event.waitUntil(
        self.registration.showNotification(title, {
            body: body,
            icon: icon,
            tag: tag
        })
    );
});