/* global naver */

/**
 * 사용자 정의 오버레이 구현하기
 */

    
    var HOME_PATH = window.HOME_PATH || '.';

    var map = new naver.maps.Map(document.getElementById('mapping_naver'), {
        center:  new naver.maps.LatLng(37.349733, 127.106970),
        zoom: 17,
    });
    
    // var marker = new naver.maps.Marker ({
    //     position: new naver.maps.LatLng(37.349733, 127.106970),
    //     map:map
    // });
    var markerOptions = {
        position: new naver.maps.LatLng(37.349733, 127.106970),
        map:map,
        icon: {
            content: [ '<div id="customoverlay">',
                        '<span class="title_map">',
                            'KALL',
                        '</span>',
                    '</div>'].join(''),
            size: new naver.maps.Size(38,58),
            anchor: new naver.maps.Point(60,67)

        }
    }

    var marker = new naver.maps.Marker(markerOptions);
    // var markers = [];
    // var infowindows = [];

    // var KALL = new naver.maps.LatLng(37.349733, 127.106970);

    // markers.push(new naver.map.Marker({
    //     map: map,
    //     position: KALL
    // }));
    

    // infowindows.push(new naver.maps.InfoWindow({
    //     content: [
    //         '<div id="customoverlay">'+
    //             '<span class="title_map">'+
    //                 'KALL'+
    //             '</span>'+
    //         '</div>'
    //     ]
    // }));

    // infowindows[0].open(map, markers[0]);