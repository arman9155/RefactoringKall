
// var Y_point = 37.349733;    // lat 값
// var X_point = 127.106970;   // lng 값

// var zoomLevel = 17;  // 첫 로딩시 보일 지도의 확대 레벨

// var myLatlng = new google.maps.LatLng(Y_point, X_point);

// var mapOptions = {
//     zoom: zoomLevel,
//     center: myLatlng,
//     mapTypeId: google.maps.MapTypeId.ROADMAP
// }

// var map = new google.maps.Map(document.getElementById('mapping_google'), mapOptions);


// var marker;

// marker = new google.maps.Marker ({
//     position: myLatlng,
//     map:map,
//     content: content
// });


// var content = '<div id="customgoogle"><span class="title_map">KALL</span></div>';

var myLatlng = new google.maps.LatLng(37.349733, 127.106970);

var mapOptions = {
    zoom: 18,
    center: myLatlng,
    mapTypeId: google.maps.MapTypeId.ROADMAP
}

var img = {
    url: "../visit/img/marker.png",
    scaledSize: new google.maps.Size(70, 70),
    labelOrigin: new google.maps.Point(35, 30)
};


var map = new google.maps.Map(document.getElementById('mapping_google'), mapOptions);

var markerOption = {
    position: myLatlng,
    map: map,
    label: {
        text: 'KALL',
        color: 'white',
        fontSize:'20px',
        fontWeight:'bold'
        },
    icon: img,
    
}

var marker = new google.maps.Marker(markerOption);

