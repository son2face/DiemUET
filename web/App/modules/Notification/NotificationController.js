/**
 * Created by Son on 4/28/2017.
 */
angular.module('managerApp').controller('NotificationController', ['$scope', '$http', function ($scope, $http) {
    function servicewo() {
        if ('serviceWorker' in navigator) {
            navigator.serviceWorker.register('sw.js').then(initialiseState);
        } else {
            console.warn('Service workers are not supported in this browser.');
        }
    }
    /**
     * Step two: The serviceworker is registered (started) in the browser. Now we
     * need to check if push messages and notifications are supported in the browser
     */
    function initialiseState() {
        // Check if desktop notifications are supported
        if (!('showNotification' in ServiceWorkerRegistration.prototype)) {
            console.warn('Notifications aren\'t supported.');
            return;
        }
        // Check if user has disabled notifications
        // If a user has manually disabled notifications in his/her browser for
        // your page previously, they will need to MANUALLY go in and turn the
        // permission back on. In this statement you could show some UI element
        // telling the user how to do so.
        if (Notification.permission === 'denied') {
            console.warn('The user has blocked notifications.');
            return;
        }

        // Check is push API is supported
        if (!('PushManager' in window)) {
            console.warn('Push messaging isn\'t supported.');
            return;
        }

        navigator.serviceWorker.ready.then(function (serviceWorkerRegistration) {

            // Get the push notification subscription object
            serviceWorkerRegistration.pushManager.getSubscription().then(function (subscription) {

                // If this is the user's first visit we need to set up
                // a subscription to push notifications
                if (!subscription) {
                    subscribe();

                    return;
                }

                // Update the server state with the new subscription
                sendSubscriptionToServer(subscription);
            })
                .catch(function (err) {
                    // Handle the error - show a notification in the GUI
                    console.warn('Error during getSubscription()', err);
                });
        });
    }

    /**
     * Step three: Create a subscription. Contact the third party push server (for
     * example mozilla's push server) and generate a unique subscription for the
     * current browser.
     */
    function subscribe() {
        navigator.serviceWorker.ready.then(function (serviceWorkerRegistration) {

            // Contact the third party push server. Which one is contacted by
            // pushManager is  configured internally in the browser, so we don't
            // need to worry about browser differences here.
            //
            // When .subscribe() is invoked, a notification will be shown in the
            // user's browser, asking the user to accept push notifications from
            // <yoursite.com>. This is why it is async and requires a catch.
            serviceWorkerRegistration.pushManager.subscribe({userVisibleOnly: true}).then(function (subscription) {

                // Update the server state with the new subscription
                return sendSubscriptionToServer(subscription);
            })
                .catch(function (e) {
                    if (Notification.permission === 'denied') {
                        console.warn('Permission for Notifications was denied');
                    } else {
                        console.error('Unable to subscribe to push.', e);
                    }
                });
        });
    }

    /**
     * Step four: Send the generated subscription object to our server.
     */
    function sendSubscriptionToServer(subscription) {

        // Get public key and user auth from the subscription object
        var key = subscription.getKey ? subscription.getKey('p256dh') : '';
        var auth = subscription.getKey ? subscription.getKey('auth') : '';

        // This example uses the new fetch API. This is not supported in all
        // browsers yet.
        return fetch('/diemuet/v1/subcribe', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                endpoint: subscription.endpoint,
                mssv: document.getElementById("mssv").value,
                // Take byte[] and turn it into a base64 encoded string suitable for
                // POSTing to a server over HTTP
                key: key ? btoa(String.fromCharCode.apply(null, new Uint8Array(key))) : '',
                auth: auth ? btoa(String.fromCharCode.apply(null, new Uint8Array(auth))) : ''
            })
        });
    }

    $scope.test = function () {
        $http.get("v1/subcribe/" + $scope.mssv)
            .then(function (response) {
                console.log("test");
            });
    }
}]);