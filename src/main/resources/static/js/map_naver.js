/* global naver */

/**
 * 사용자 정의 오버레이 구현하기
 */

    
    var HOME_PATH = window.HOME_PATH || '.';

    var map = new naver.maps.Map(document.getElementById('mapping_naver'), {
        center:  new naver.maps.LatLng(37.404340, 127.116291),
        zoom: 17,
    });
    
    // var marker = new naver.maps.Marker ({
    //     position: new naver.maps.LatLng(37.349733, 127.106970),
    //     map:map
    // });
    var markerOptions = {
        position: new naver.maps.LatLng(37.404340, 127.116291),
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
