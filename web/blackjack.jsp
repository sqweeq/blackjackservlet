<%--
    Document   : newjsp
    Created on : 5/05/2021, 3:20:55 am
    Author     : Brandon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <h1>Play Blackjack!</h1>
        <!--<img src="images/12H.jpg" style="width:150px;height:auto;" />-->
        <input onclick="postStart();" type="button" value="start new game" />
        <input id="hitButton" onclick=postHit(); type="button" value="move hit"  disabled='true' />
        <input id="standButton" onclick=postStand(); type="button" value="move stand" disabled='true' />
        <!--<input  type="button" value="post won" />-->
        <!--<input onclick=getPossibleMoves(); type="button" value="get possiblemoves" />-->
        <input onclick=getStats(); type="button" value="get stats" />



        <div id="gamesPlayedId">games played:  </div>
        <div id="userWinId">user games won:  </div>
        <div id="winnerId">winner:  </div>
        <div id="whoseTurnId">whose turn:  </div>
        <div id="playerHandId">player hand: </div>
        <div id="playerCountId">player count final: </div>
        <div id="dealerHandId">dealer hand :</div>
        <div id="dealerCountId">dealer count final: </div>

        <!--<div id="deckId">cards :</div>-->
        <%@ page isThreadSafe="false"  %>


    </body>
    <script>
        var dealerShow;
        function getState() {
            const Http = new XMLHttpRequest();
            const url = 'http://localhost:8080/assignment2_server_21008007/jack/state';
            Http.open("GET", url);
            Http.setRequestHeader('Content-Type', 'application/json');
            Http.send();
            Http.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    var data = JSON.parse(this.response);
                    if (data) {
                        var deckOfCards = data[0];
                        var playerHand = data[1];
                        var dealerHand = data[2];
                        var playerFinalCount = data[3];
                        var dealerFinalCount = data[4];
                        var whoseTurn = data[5];
                        var winner = data[6];
                        var gamesPlayed = data[7];
                        var userWin = data[8];
                        //                        var deckDiv = document.getElementById('deckId');
                        var playerDiv = document.getElementById('playerHandId');
                        var dealerDiv = document.getElementById('dealerHandId');
                        var gamesPlayedDiv = document.getElementById('gamesPlayedId');
                        var userWinDiv = document.getElementById('userWinId');
                        var winnerDiv = document.getElementById('winnerId');
                        var whoseTurnDiv = document.getElementById('whoseTurnId');
                        var playerCountDiv = document.getElementById('playerCountId');
                        var dealerCountDiv = document.getElementById('dealerCountId');
                        if (deckOfCards && playerFinalCount) {
                            gamesPlayedDiv.innerHTML = "games played:  " + gamesPlayed;
                            userWinDiv.innerHTML = "user games won:  " + userWin;
                            winnerDiv.innerHTML = "winner:  " + winner;
                            whoseTurnDiv.innerHTML = "whose turn:  " + whoseTurn;
                            playerCountDiv.innerHTML = "player final count:  " + playerFinalCount;
                            if (whoseTurn === "dealer") {
                                dealerCountDiv.innerHTML = "dealer final count:  " + dealerFinalCount;


                            } else {
                                dealerCountDiv.innerHTML = "dealer final count:  ?";
                            }
                            //                            deckDiv.textContent = 'cards: ';
                            playerDiv.textContent = 'player hand: ';
                            dealerDiv.textContent = 'dealer hand: ';
                            //                            displayCards(deckOfCards, deckDiv);
                            displayCards(playerHand, playerDiv);
                            if (whoseTurn === "user" || null) {
                                displayCardsDealer(dealerHand, dealerDiv);
                            } else {
                                displayCards(dealerHand, dealerDiv);
                            }
                            dealerShow = dealerHand;
                        }

                    }

                    getPossibleMoves();
                }
            }
        }
        function showHand(array) {
            if (array) {
                var dealerDiv = document.getElementById('dealerHandId');
                dealerDiv.textContent = 'dealer hand: ';
                for (i = 0; i < array.length; i++) {
                    var splitString = array[i].split(" ").join("");
                    var cardImg = document.createElement('img');
                    cardImg.src = "images/" + splitString + ".jpg"
                    cardImg.style = "width:120px;height:auto;"
                    dealerDiv.appendChild(cardImg);
                }
            }
        }

        function displayCards(array, div) {
            if (array) {
                for (i = 0; i < array.length; i++) {
                    var splitString = array[i].split(" ").join("");
                    var cardImg = document.createElement('img');
                    cardImg.src = "images/" + splitString + ".jpg"
                    cardImg.style = "width:120px;height:auto;"
                    div.appendChild(cardImg);
                }
            }
        }
        function displayCardsDealer(array, div) {
            if (array) {
                var cardBack = document.createElement('img');
                cardBack.src = "images/Red_back.jpg";
                cardBack.style = "width:120px;height:auto;"
                div.appendChild(cardBack);
                for (i = 1; i < array.length; i++) {
                    var splitString = array[i].split(" ").join("");
                    var cardImg = document.createElement('img');
                    cardImg.src = "images/" + splitString + ".jpg"
                    cardImg.style = "width:120px;height:auto;"
                    div.appendChild(cardImg);
                }
            }
        }
        function getStats() {
            const Http = new XMLHttpRequest();
            const url = 'http://localhost:8080/assignment2_server_21008007/jack/stats';
            Http.open("GET", url);
            Http.setRequestHeader('Content-Type', 'text/plain');
            Http.send();
            Http.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
//                    console.log(this.response);
                    let data = this.response.toString();
                    const words = data.split(' ')
                    console.log(data)
                    if (words) {
                        showAlert(words)

                    }

                }
            }
            ;
        }
        function showAlert(words) {
            alert("games played: " + words[0] + "\ngames won: " + words[1] + "\nwinning percentage: " + parseFloat(words[2]).toFixed(2) + "%");
            console.log(words)

        }

        function getPossibleMoves() {
            const Http = new XMLHttpRequest();
            const url = 'http://localhost:8080/assignment2_server_21008007/jack/possiblemoves';
            Http.open("GET", url);
            Http.setRequestHeader('Content-Type', 'text/plain');
            Http.send();
            Http.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    //                    console.log("success");
                    let data = this.response
                    //                    console.log(data);
                    const words = data.split(' ');
                    //                console.log(words)
                    if (words[0] === "none") {
                        document.getElementById('standButton').disabled = true;
                        document.getElementById('hitButton').disabled = true;
                    } else if (words.length === 1) {
                        //                        const hit = words[0];
                        const w1 = words[0];
                        //                        console.log(words[0], words[1])
                        document.getElementById('hitButton').disabled = true;
                        //                    console.log(w1)
                    } else if (words.length === 2) {
                        document.getElementById('standButton').disabled = false;
                        document.getElementById('hitButton').disabled = false;
                        const w1 = words[0];
                        const w2 = words[1];
                        //                    console.log(w1, w2)
                    }
                }
            }
            ;
        }

        function postStart() {
            const Http = new XMLHttpRequest();
            const url = 'http://localhost:8080/assignment2_server_21008007/jack/start';
            Http.open("POST", url);
            Http.setRequestHeader('Content-Type', 'application/json');
            Http.send();
            Http.onreadystatechange = (e) => {
                if (Http.status === 200) {
                    getState();
                }
            }
        }
        function postHit() {
            const Http = new XMLHttpRequest();
            const url = 'http://localhost:8080/assignment2_server_21008007/jack/move/hit';
            Http.open("POST", url);
            Http.setRequestHeader('Content-Type', 'text/html');
            Http.send();
            Http.onreadystatechange = (e) => {
                if (Http.status === 200) {
                    getState();
                    //check if "OK" (200)
                } else if (Http.status === 400) {
                    console.log("400 Bad Request, either it is not user turn, or user's hand is already bust(user hand greater than 21)")
                } else {
                    console.log("404 Not Found, there is not an active game")
                }
            }
            getState();
        }
        function postStand() {
            const Http = new XMLHttpRequest();
            const url = 'http://localhost:8080/assignment2_server_21008007/jack/move/stand';
            Http.open("POST", url);
            Http.setRequestHeader('Content-Type', 'application/json');
            Http.send();
            Http.onreadystatechange = (e) => {
                if (Http.status === 200) {  //check if "OK" (200)
                    document.getElementById('standButton').disabled = true;

                } else {
                    console.log("404 Not Found, there is not an active game")
                }
            }
            getState();
            postWon()
        }
        function postWon() {
            const Http = new XMLHttpRequest();
            const url = 'http://localhost:8080/assignment2_server_21008007/jack/won';
            Http.open("POST", url);
            Http.setRequestHeader('Content-Type', 'text/plain');
            Http.send();
            Http.onreadystatechange = (e) => {
                if (Http.status === 200) {  //check if "OK" (200)
                    document.getElementById('standButton').disabled = true;

                } else {
                    console.log("404 Not Found, there is not an active game")
                }
            }
            getState();

        }

    </script>

</html>
