/**
 * Created by djeremias on 1/13/17.
 */
abApp.controller('AppController', [ '$scope', '$rootScope', '$state', '$locale','LoginService',
    function($scope, $rootScope, $state, $locale, LoginService) {

        $scope.doLogout = function (){
            LoginService.logout().then(function(respose){
                $state.go('login', {});
            });
        };

        $scope.isLoggedIn = function(){
            return LoginService.isLoggedIn();
        };
    }
]);