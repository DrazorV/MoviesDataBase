function find_my_div(){
    $(".card").empty().remove();
    if(document.getElementById("edit_search").value.length > 0){
        Search(document.getElementById("edit_search").value.toUpperCase().trim());
    }
}

function Search(movie) {
    let queryString = "https://www.omdbapi.com/?apikey=b452b056&s=" + movie;
    // Put that query string into the AJAX request
    $.ajax({
        url: queryString, // The URL to the API. You can get this by clicking on "Show CURL example" from an API profile
        method: 'GET',
        dataType: 'json',
    }).done(function (response) {
        if(response.Response==="True"){
            let array = response.Search;
            for(let i = 0; i < array.length; i++){

                let movieContainer = $('<div class="card mb-3 text-white border-info bg-dark">');
                let header = $('<div class="card-header">');
                let footer = $('<div class="card-footer">');

                let rows = $('<div class="row no-gutters">');


                let col1 = $('<div class="col-md-4">');
                let col2 = $('<div class="col-md-8">');


                let plot = $('<div class="card-body">');

                let list = $('<ul class="list-group list-group-flush">');

                let summary = $('<div class="card-body">');

                let button = $('<button  class="btn-outline-info btn">');
                let button2 = $('<button  class="btn btn-outline-success" style="float: right">');

                button.text("More..")
                for (let prop in array[i]) {
                    let element;
                    if (prop === "Poster" && response[prop] !== "N/A") {
                        element = $("<img class='card-img' alt=''>").attr("src", array[i][prop]);
                        col1.append(element)
                    } else if (prop === "Title"){
                        element = $("<h2 class='card-title text-center'>").text(array[i][prop]);
                        header.append(element)
                    } else if (prop === "Type") {
                        //ignored
                    } else if (prop === "imdbID") {
                        button.attr("id", array[i][prop])
                        button2.attr("id", array[i][prop])

                        $.ajax({
                            url : 'http://localhost:8080/update',
                            type: 'POST',
                            data: { 'id' : array[i][prop]},
                            success: function(response) {
                                if(response.movieId === array[i][prop]){
                                    button2.text("Bookmarked")
                                    button2.attr("class","btn btn-outline-danger");
                                }else{
                                    button2.text("Bookmark")
                                    button2.attr("class","btn btn-outline-success");
                                }
                            }
                        });

                        $.ajax({
                            url:"https://www.omdbapi.com/?apikey=b452b056&i=" + array[i][prop] + "&plot=full",
                            type: "post",
                            success: function(data){
                                element = $("<h5 class='card-title text-white'>").text("Plot");
                                plot.append(element)

                                element = $("<p class='card-text text-white'>").text(data.Plot);
                                plot.append(element);

                                element = $("<li class='list-group-item text-white bg-dark'>").text("📊 Metascore: " + data.Metascore + "%");
                                list.append(element);

                                element = $("<li class='list-group-item text-white bg-dark'>").text("⌛ Year: " + data.Year);
                                list.append(element);

                                element = $("<li class='list-group-item text-white bg-dark'>").text("🎛️ Genre: " + data.Genre);
                                list.append(element);

                                element = $("<li class='list-group-item text-white bg-dark'>").text("✍🏻 Writer: " + data.Writer);
                                list.append(element);

                                element = $("<li class='list-group-item text-white bg-dark'>").text("🎬 Director: " + data.Director);
                                list.append(element);

                                element = $("<li class='list-group-item text-white bg-dark'>").text("⏲️ Runtime: " + data.Runtime);
                                list.append(element);
                            }
                        });
                    }
                }

                footer.append(button)
                footer.append(button2)
                col2.append(list)
                rows.append(col1);
                rows.append(col2);

                summary.append(rows)

                movieContainer.append(header)
                movieContainer.append(summary)
                movieContainer.append(plot)
                movieContainer.append(footer)

                $("#layout").append(movieContainer);
            }
        }
    });
}

$(document).on('click', '#bookmarks', function () {
    $(".card").empty().remove();

    $.ajax({
        url : 'http://localhost:8080/getBookmarks',
        type: 'POST',
        success: function(response) {
            for(let i in response){
                $.ajax({
                    url:"https://www.omdbapi.com/?apikey=b452b056&i=" + response[i].movieId + "&plot=full",
                    type: "post",
                    success: function(data){
                        let movieContainer = $('<div class="card mb-3 text-white border-info bg-dark">');
                        makeCard(data,movieContainer,true)
                        $("#layout").append(movieContainer);
                    }
                })
            }
        }
    });
})

$(document).on('click', '.btn-outline-success', function () {
    let button = this;
    $.ajax({
        url : 'http://localhost:8080/bookmark',
        type: 'POST',
        data: { 'id' : button.id},
        success: function(response) {
            button.innerHTML = "Bookmarked";
            button.className ="btn btn-outline-danger";
        }
    });
})

$(document).on('click', '.btn-outline-danger', function () {
    let button = this;
    $.ajax({
        url : 'http://localhost:8080/bookmarked',
        type: 'POST',
        data: { 'id' : button.id},
        success: function(response) {
            button.innerHTML = "Bookmark";
            button.className ="btn btn-outline-success";
        }
    });
})

$(document).on('click', '.btn-outline-info', function () {
    $(".card").empty().remove();
    $.ajax({
        url:"https://www.omdbapi.com/?apikey=b452b056&i=" + this.id + "&plot=full",
        type: "post",
        success: function(data){
            let movieContainer = $('<div class="card mb-3 text-white border-info bg-dark" style="margin: 0 15% 0 15%">');
            makeCard(data,movieContainer,false)
            $(".container-login100").append(movieContainer);
        }

    });
});

$(document).on('click', '.btn-outline-secondary', function () {
    find_my_div()
});



function makeCard(data,movieContainer,bookmark) {
    let element;

    let header = $('<div class="card-header">');
    let footer = $('<div class="card-footer">');

    let rows = $('<div class="row no-gutters">');


    let col1 = $('<div class="col-md-4">');
    let col2 = $('<div class="col-md-8">');


    let plot = $('<div class="card-body">');

    let list = $('<ul class="list-group list-group-flush">');

    let summary = $('<div class="card-body">');


    let button2 = $('<button  class="btn btn-outline-success" style="float: right">');

    let button;

    if(bookmark){
        button = $('<button  class="btn-outline-info btn">');
        button.text("More..")
    }else{
        button = $('<button  class="btn-outline-secondary btn">');
        button.text("Back");
    }

    button.attr("id", data.imdbID);
    button2.attr("id", data.imdbID);

    $.ajax({
        url : 'http://localhost:8080/update',
        type: 'POST',
        data: { 'id' : data.imdbID},
        success: function(response) {
            if(response.movieId === data.imdbID){
                button2.text("Bookmarked")
                button2.attr("class","btn btn-outline-danger");
            }else{
                button2.text("Bookmark")
                button2.attr("class","btn btn-outline-success");
            }
        }
    });

    element = $("<img class='card-img' alt=''>").attr("src", data.Poster);
    col1.append(element)

    element = $("<h2 class='card-title text-center'>").text(data.Title);
    header.append(element)

    element = $("<h5 class='card-title text-white'>").text("Plot");
    plot.append(element)

    element = $("<p class='card-text text-white'>").text(data.Plot);
    plot.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("📊 Metascore: " + data.Metascore + "%");
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("⌛ Year: " + data.Year);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("📆 Release Date: " + data.Released);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("🎛️ Genre: " + data.Genre);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("✍🏻 Writer: " + data.Writer);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("🧛️ Actors: " + data.Actors);
    list.append(element);


    element = $("<li class='list-group-item text-white bg-dark'>").text("🎬 Director: " + data.Director);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("⏲️ Runtime: " + data.Runtime);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("🌍 Language: " + data.Language);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("✍🗺️ Country: " + data.Country);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("🏆 Awards: " + data.Awards);
    list.append(element);

    for(let i = 0; i < data.Ratings.length; i++){
        element = $("<li class='list-group-item text-white bg-dark'>").text("⭐ " + data.Ratings[i].Source + " Rating: " + data.Ratings[i].Value);
        list.append(element);
    }

    element = $("<li class='list-group-item text-white bg-dark'>").text("⭐ IMDB Rating: " + data.imdbRating);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("✍🗳️ IMDB Votes: " + data.imdbVotes);
    list.append(element);

    element = $("<li class='list-group-item text-white bg-dark'>").text("🆎 Type: " + data.Type);
    list.append(element);

    footer.append(button)
    footer.append(button2)
    col2.append(list)
    rows.append(col1);
    rows.append(col2);

    summary.append(rows)

    movieContainer.append(header)
    movieContainer.append(summary)
    movieContainer.append(plot)
    movieContainer.append(footer)
}