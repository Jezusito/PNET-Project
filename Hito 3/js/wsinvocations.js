// Recibe una reserva en forma de json y devuelve el html en forma de cadena equivalente junto con sus botones
function jsonToHtml(booking) {
    let string = "<div>";
    string += "<p> <span class='keys'>" + Object.keys(booking)[0] + ": </span>"
     + booking[Object.keys(booking)[0]] + "</p>"
    for(var i = 1; i < Object.keys(booking).length; ++i)
    {
        string += "<p> <span class='keys'> <label for='" + booking._id + Object.keys(booking)[i] + "'>" 
            + Object.keys(booking)[i] + ": </label> </span>" 
            + "<input type='text' id='" + booking._id + Object.keys(booking)[i] + "' value='" 
            + booking[Object.keys(booking)[i]] + "'> </p>";
    }
    string += '<input type="button" value="Update Booking" id="' + booking._id + 'put" />'
    string += '<input type="button" value="Delete Booking" id="' + booking._id + 'delete" />'
    string += "</div>"
    return string;
}

// Recibe una lista de reservas y devuelve sus respectivos html en forma de cadena
function jsonsToHtml(bookings) {
    let string = "";
    for (var i = 0; i < Object.keys(bookings).length; ++i)
        string += jsonToHtml(bookings[i]);
    return string;
}

// Asigna los eventos onclick de los botones de una reserva
function insertOnClick(booking) { 
    $("#" + booking._id + "put").click(function () { putBooking(booking) }); 
    $("#" + booking._id + "delete").click(function () { deleteBooking(booking._id) });
}

// Asigna los eventos onclick de los botones de un conjunto de reservas
function insertAllOnClick(bookings) { bookings.forEach(booking => {insertOnClick(booking);}); }

// Devuelve un json con los valores introducidos en los campos de una reserva
function getBookingInputValues(booking){
    let data = {};
    for (var i = 1; i < Object.keys(booking).length; ++i)
        data[Object.keys(booking)[i]] = $("#" + booking._id + Object.keys(booking)[i]).val()
    return data
}  

//Recibe el ID de una reserva y la busca para mostrarla en pantalla
function getBooking(bookingId) {
    var myUrl = "http://localhost:8080/bookings/" + bookingId;
    $.ajax({
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data) {
            $("#resBooking").html(jsonToHtml(data[0]));
            insertOnClick(data[0])
        },
        error: function(res) {
            alert("ERROR:" + res.statusText);
        }
    });
}

//Añade una reserva 
function postBooking(booking) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/bookings/",
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify(getBookingInputValues(booking)),
        success: function(data) {
            $("#resBooking").html(data);
        },
        error: function(res) {
            alert("ERROR: " + res.statusText);
        }
    });
}

//Obtiene y muestra todas las reservas en pantalla
function getAllBookings() {
    var myUrl = "http://localhost:8080/bookings/";
    $.ajax({
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data) {
            $("#resBooking").html(jsonsToHtml(data));
            insertAllOnClick(data);
        },
        error: function(res) {
            alert("ERROR " + res.statusText);
        }
    });
}

//Recibe un ID de una reserva y si existe, lo borra de la base de datos
function deleteBooking(bookingId) {
    var myUrl = "http://localhost:8080/bookings/" + bookingId;
    $.ajax({
        type: "DELETE",
        dataType: "text",
        url: myUrl,
        success: function (data) {
            $("#resBooking").html(data);
        },
        error: function (res) {
            alert("ERROR " + res.statusText);
        }
    });
}

//Recibe una reserva y actualiza sus valores con los introducidos en sus campos de entrada
function putBooking(booking) {
    var myUrl = "http://localhost:8080/bookings/" + booking._id;
    $.ajax({
        type: "PUT",
        url: myUrl,
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify(getBookingInputValues(booking)),
        success: function (data) {
            $("#resBooking").html(data);
        },
        error: function (res) {
            alert("ERROR: " + res.statusText);
        }
    });
}

