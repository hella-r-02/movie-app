package com.app.data.models

object MoviesDataSource {
    fun getMovies(): List<Movie> {
        return listOf(
            Movie(
                id = 1,
                name = "Avengers:End Game",
                poster = "https://play-lh.googleusercontent.com/XDg-bt655am_Q-7X-I0s64Kq8SJKfb7BBTHkUVbFR6-zDNv9J7rW61xZn0BB3SVCJ6gz",
                pg = "13+",
                rating = 4,
                storyline = "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                countOfReviews = "125",
                duration = "137",
                genre = "Action, Comedy",
                actors =
                listOf(
                    Actor(
                        id = 1,
                        name = "Robert Downey Jr.",
                        avatar = "https://cdn.smartfacts.ru/355308/conversions/robert-downey-jr_0-medium.jpg"
                    ),
                    Actor(
                        id = 2,
                        name = "Chris Evans",
                        avatar = "https://upload.wikimedia.org/wikipedia/commons/8/89/Chris_Evans_2020_%28cropped%29.jpg"
                    ),
                    Actor(
                        id = 3,
                        name = "Mark Ruffalo",
                        avatar = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/11/Mark_Ruffalo_%2836201774756%29_%28cropped%29.jpg/1200px-Mark_Ruffalo_%2836201774756%29_%28cropped%29.jpg"
                    ),
                    Actor(
                        id = 4,
                        name = "Chris Hemsworth",
                        avatar = "https://upload.wikimedia.org/wikipedia/commons/e/e8/Chris_Hemsworth_by_Gage_Skidmore_2_%28cropped%29.jpg"
                    )
                )
            ),
            Movie(
                id = 2,
                name = "Spider-Man: Far From Home",
                poster = "https://play-lh.googleusercontent.com/_gIJ5eszlkwDw7tWekePeapreSRDlQti4XtsivYKUOUq8I-wEqROEv8kjHm3EXyfdrvjS92d-VDQFNR-AyA",
                pg = "13+",
                rating = 4,
                storyline = "Peter Parker returns in Spider-Man: Far From Home, the next chapter of the Spider-Man: Homecoming series! Our friendly neighborhood Super Hero decides to join his best friends Ned, MJ, and the rest of the gang on a European vacation. However, Peter's plan to leave super heroics behind for a few weeks are quickly scrapped when he begrudgingly agrees to help Nick Fury uncover the mystery of several elemental creature attacks, creating havoc across the continent!",
                countOfReviews = "3",
                duration = "129",
                genre = "Action, Adventure, Fantasy",
                actors =
                listOf(
                    Actor(
                        id = 1,
                        name = "Tom Holland",
                        avatar = "https://static.wikia.nocookie.net/marvelcinematicuniverse/images/2/2f/Tom_Holland.jpg/revision/latest?cb=20220213070224&path-prefix=ru"
                    ),
                    Actor(
                        id = 2,
                        name = "Zendaya",
                        avatar = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Zendaya_-_2019_by_Glenn_Francis.jpg/800px-Zendaya_-_2019_by_Glenn_Francis.jpg"
                    )
                )
            ),
        )
    }
}