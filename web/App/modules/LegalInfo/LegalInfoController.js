/**
 * Created by Son on 4/28/2017.
 */
angular.module('managerApp').controller('LegalInfoController', ['$scope', '$http', 'NgTableParams', function ($scope, $http, NgTableParams) {
    $scope.database = [];
    // $scope.baseUrl = "http://skylab.uet.vnu.edu.vn:8080/api/";
    $scope.a = "aaaaaa";
    $scope.baseUrl = "";
    $scope.getData = function () {
        $http.get("v1/legal/info?limit=1000")
            .then(function (response) {
                $scope.database = response.data.data;
                $scope.database.forEach(function (item,index) {
                    var temp = new Date(item.dateExecute);
                    var temp1 = new Date(item.dateCreated)
                    item.dateExecuteString =  temp.getDate()+"/"+(temp.getMonth()+1)+"/"+temp.getFullYear();
                    item.dateCreatedString =  temp1.getDate()+"/"+(temp1.getMonth()+1)+"/"+temp1.getFullYear();
                })
                $scope.xxx = new NgTableParams({}, {dataset: $scope.database});
            });
    };
    $scope.delete = function (data) {
        var answer = confirm('Bạn có muốn xóa không?');
        if (answer) {
            $http.delete($scope.baseUrl + "v1/legal/info/" + data.id)
                .then(function (response) {
                    var index = $scope.database.indexOf(data);
                    if (index !== -1) {
                        $scope.database.splice(index, 1);
                    }
                    $scope.xxx = new NgTableParams({}, {dataset: $scope.database});
                });
        }
    };
    $scope.show = function (data) {
        $http.get($scope.baseUrl + "v1/managers/database")
            .then(function (response) {
                $scope.database = response.data.data;
                console.log($scope.database);
            });
    };
    $scope.edit = function (data) {
        $http.put($scope.baseUrl + "v1/managers/database" + data.id)
            .then(function (response) {
                $scope.database = response.data.data;
                console.log($scope.database);
            });
    };
    $scope.create = function () {
        var x = "url=" + $scope.url + "&username=" + $scope.user + "&password=" + $scope.pass + "&dbName=" + $scope.schema + "&type=" + $scope.type;
        $http.post($scope.baseUrl + "v1/managers/database", x, {headers: {'Content-Type': 'application/x-www-form-urlencoded'}})
            .then(function (response) {
                $scope.getData();
                console.log("success");
            });
    };
    $scope.getData();

// Get the modal

// Get the button that opens the modal
//     var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
//     var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
    $scope.btnClick = function () {
        console.log(document.getElementById("myModal"));
        document.getElementById("myModal").style.display = "block";
    };

// When the user clicks on <span> (x), close the modal
    $scope.spanClick = function () {
        document.getElementById("myModal").style.display = "none";
    };

// When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == document.getElementById("myModal")) {
            document.getElementById("myModal").style.display = "none";
        }
    }
}]);
