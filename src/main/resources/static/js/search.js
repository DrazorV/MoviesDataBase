
function gid(a_id) {
    return document.getElementById (a_id)	;
}

function close_all(){// hide all divs

    for (let i = 0; i < 7; i++) {// to simplify the story I have here the hardcoded number of elements. In real life make it better.
        let o = gid("user_"+i);
        if (o) { //just to make sure this object really exists
            o.style.display = "none";
        }
    }

}

function find_my_div(){
    close_all();

    let str_needle = gid("edit_search").value.toUpperCase();
    if (str_needle !== "") {// we will not search for empty strings
        for (let i = 0; i < 7; i++) {

            let o = gid("user_"+i);
            if (o) { //just to make sure this object really exists
                // we want case insensitive search, so bring the both parts to upper case and compare
                let str_haystack = o.innerHTML.toUpperCase();
                if (str_haystack.indexOf(str_needle) === -1) {
                    // not found, do nothing
                }
                else{
                    // yes, we got it, show it
                    o.style.display = "block";
                }
            }
        }
    }
}

(function(angular) {
    'use strict';
    angular.module('OMDbAPISearch', [])
        .controller('searchMovies', ['$scope', '$http',
            function($scope, $http) {
                $scope.method = 'GET';
                $scope.fetch = function() {
                    if ($scope.searchparam) {
                        $scope.url = 'https://www.omdbapi.com/?s=' + $scope.searchparam + '&apikey=b452b056';
                        $http({
                            method: $scope.method,
                            url: $scope.url
                        }).
                        then(function(response) {
                            if (response.data.Response) {
                                // success
                                $('.results').css('display', 'block');
                                $('.noResults').css('display', 'none');
                                let theSrchResults = response.data["Search"];
                                angular.forEach(theSrchResults, function(obj) {
                                    // loop through each movie, and pull the details using the IMDB ID
                                    $http({
                                        method: $scope.method,
                                        url: 'https://www.omdbapi.com/?i=' + obj.imdbID + '&plot=full&r=json&tomatoes=true'
                                    }).
                                    then(function(response) {
                                        // extend the details to the parent
                                        obj.details = response.data;
                                    });
                                });
                                $scope.movieResults = theSrchResults;
                            } else {
                                //error, movie not found
                                console.log("not found");
                                $('.results').css('display', 'none');
                                $('.noResults').css('display', 'block');
                                $('.noResults').html("<strong>No results found.</strong>");
                            }
                        }, function(response) {
                            console.log("failure");
                            $('.results').css('display', 'none');
                            $('.noResults').css('display', 'block');
                            $('.noResults').html("<strong>Network or data error, please try again.</strong>");
                        });
                    } else {
                        // no input value
                        $('.results').css('display', 'none');
                        $('.noResults').css('display', 'none');
                        $('#theSearch').fadeIn(100).fadeOut(100).fadeIn(100).fadeOut(100).fadeIn(100);
                    }
                };
            }
        ])
        .directive('movieSrchResults', function() {
            return {
                templateUrl: '../../templates/movieResults.html'
            };
        });
})(window.angular);