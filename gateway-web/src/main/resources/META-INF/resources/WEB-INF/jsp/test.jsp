<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>申请网关交互主页_v1</title>
    <link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css"/>
    <script src="https://unpkg.com/jquery@1.12.4/dist/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            //默认partner=1
            ////$('#publicKey').val("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCsYlvVotdm1bbAhrclF/58Jx6zZku4HZFA3o4GEFvxgvTwSl7zl1T/vkbMV47+z27B7g67VmlYj92/TJUbpcLwz241lZyZqow5eJjcVRKr/NGEMS1HJxCmM4HMNNR9I53fVdofw/dXPi8VcsIN9BXFHJibK8RRY9rKKAB8pz5hOQIDAQAB");
            ////$('#privateKey').val("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMGpXwBQyV+rX4fA0MQHSPJwrZJVSG3qLtKZYI4scgCfGnBdTzRUkF73CDFhEKvz2dsWl4ELGRA6siXXVhY+t/ITA7HvgNkKiBDN2YYs6m6/ccahBU4Zmy5kMzAC8e4dkhwGMpTCqONT/q8JK4yD8OEIhXoglt1dqt99ouGfd1UVAgMBAAECgYEAutapLSjD32T7GYHQ6A8PNbe3/cIByulwmwYWvZPruOXHIDklvRsI3lX+tYUsDHmK6XB2Yq1OVPYMAa4rA/Mmitk47Tyfk7czNijSZpVUL/jlIemR4V5zRGb82nI2mFxSBhM4J7jc5Lgh0Qi/zT2lVkxA1ZwZiWdnltfNF8vAzFECQQD7XPDpPHR4WBHvV+vwdbEygjqlxyWflFqFr8A/e4w8gsADKJz119kHhsMtQRcMQV5diGo2iJ6++umTO3lcySg7AkEAxTvviAE+22UUFb8Q9yUWHllv1tH+Xg4BvdN8AtB/w5MlxMI+Np5LNJ5dR+tXb3r063MWHQJ7DqApOZl3/7ny7wJBAIhhZvzFJlRdRG3eAE+ZK6lB61wi7xDkRzEdqGtJN09liZStJmxkzoY84gzdsgmeNfr9ltM70RXBwZV2wWRMaB0CQB9KM+TytaMetQICLtHWi1zDOY/t/pmzRSl1TJ4xr6M6S+qcGjyV/j5ZxVisiFGCQyq7Nk3h393hIMknhLcIK/cCQHA6Q+oVcVHeJMmbUsoRfhZsDufjM98qufU5ajlnV4KA7ZZHL53YDWIQmxH6SYFBeXfioo8wXCtjNbwxvyXlfh4=");
            //$('#publicKey').val("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYjBjsOlKakhz8QhXP/gwGVAGEc7XGeTY/ZAE39xJP3tJ/wlm2uNXBiQpjKqzmUdGYtIDTjRs6oBLc4F5VIliLL0jATG/61UyJwp8lfBJSFnBgs6wku3z2clHcyM0yVrhrlPj+A749zd5NEQm9Hg6iss0Vp/FF7MdDyJ1J5+HtXwIDAQAB");
            //$('#privateKey').val("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANiMGOw6UpqSHPxCFc/+DAZUAYRztcZ5Nj9kATf3Ek/e0n/CWba41cGJCmMqrOZR0Zi0gNONGzqgEtzgXlUiWIsvSMBMb/rVTInCnyV8ElIWcGCzrCS7fPZyUdzIzTJWuGuU+P4Dvj3N3k0RCb0eDqKyzRWn8UXsx0PInUnn4e1fAgMBAAECgYEAw6NmzN0wTded+kWWQVBK+tigelKzsjlGSmUGONKuJ5yu3p/uv4ckD3LJAKjIhlj7HJRPI1GDfSarhAPtFcsu4/7wgr1irfHNOEf7fTDwRB9ahi8VRdNUqRxnfK5Z1U7Q0YRKmtIG9IO8O5+nVqtk0g8/yF3uQJ0+CxiPa5uWMPECQQDwV2/Ky6GMmn6FXhLMmb6hyYdXWfMcA+uPDLi+XVm1PU0vn0v/AQptq4CyQGl2/0yQUYksI+seFQUGmlFwETEHAkEA5qfODIH5ZD/fb3G+ZwACobtZfWh6eV/41PTGwz7Y+msQQuhS84lCFPe8SEph6/1xc4oBIysr7rFhAvxFwgPC6QJBAOPTPpmSJfVOjhiGcHOaWearTqwzDAzGeokDmix5+QugrIY0DqoPpn8HT9uoy1CN+VnksI8zDTFa44S6y+/bnAkCQQC2dqClfFxRVcXnX0+EI5nxX0fX0UKxqZ4quMhTw+/ZicB/bVHRng9Mjv2/Ijh8ey24fL+RlqFWXbOkrPRyGo+hAkB/HpGMdyfNCnSm57rmE9mw8X5+R5nKkBF/tthdSXyB475Z1YgZbthDXvYj7wN1uvijeVpyCMB9x1j7MDw9b/GP");

            //$('#partner').val(1);

            //TODO:
            //$('#publicKey').val("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDxAbF5OMdlj/NvI5tiohgj59N1R8MgJKJjddSCizDFJQmXU7L92iXDd+kflZpl8r7/GVswYKQGLhnm8kyrX+/daw+Ur5ianEjuXaMlJFnFZkaJGNWLx+odknLWYWnfUMEfvUy5kS2xNBXiPz9GjwNmgIj2HeeBnrIMsSmgE408mwIDAQAB");
            //$('#privateKey').val("MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAPEBsXk4x2WP828jm2KiGCPn03VHwyAkomN11IKLMMUlCZdTsv3aJcN36R+VmmXyvv8ZWzBgpAYuGebyTKtf791rD5SvmJqcSO5doyUkWcVmRokY1YvH6h2SctZhad9QwR+9TLmRLbE0FeI/P0aPA2aAiPYd54GesgyxKaATjTybAgMBAAECgYEAplzL3GjUQ4hNux8yKLDJxydE8YU67Vo8ejmhGwfn/35kk4AkY1UNklOYqcPEU7FwJHmlV8yuDNIP8Tq6r+XGlZNN8fc2BQ8RuCdASpJ21wrDVJdpy/gAs5hylAXkod0Fa5Pf6Yegu2YtKwKaVI/DzNaIIG/Zebnye13KAQuNPOkCQQD9gc09SkHQPcymhKtzcE9hPbWAo656Xo2/WaNaMQyueeA/Qe7gJ0jDxwZMOXWee84Km/8ndNudtXCgS/GdS7L1AkEA82BsCGaqwOE+Zm1dvODIfPRSlIgEnyLXedXCpZHFDItAo7F54VsMB8wn8W4/wBUUeNzfA78hR3djOKsHu4kXTwJBAOagff1yXul6L4KWU/xTgoPuxf7f6k29U6tveyMEWIsqqY4jB5S5aINjvyD9bTnfXBVe0gQtVdbmSC4sqQT25zkCQQC0KgXvdikjndq2snF4+CIStj9HqyVYtM80ZvSv4qgvcAqK4z/pfp/6Sbyr8kSJKlG8Yy1Itb2qDQxLj/iqcILrAkEAqvOfjxgWL4mx3MVvyAKHXTry4Wa1gZV3bZJqtSMnm9JDg3Nv8cn90CcUS2/saLZMefziJ7EVb//0WNqBu+QmWg==");
            $('#partner').val(100013);

            $('#responseBody').val(new Date().getTime());
            $(".sha256").show();

            $("#algorithm1").click(function () {
                $("#sha256").show();
            });

            $('#makeSign').click(function(){
                var algorithm = null;
                var url = "${ctx}";
                if ($("#algorithm1").is(':checked')) {
                    algorithm = 1;
                    url += "/mock/sha256";
                }

                var requestBody = $("#requestBody").val();
                var partner = $("#partner").val();
                var version = $("#version").val();
                var signature = $("#signature").val();
                //var publicKey = $("#publicKey").val();
                //var privateKey = $("#privateKey").val();
                $.ajax({
                    url: url,
                    data: {
                        partner: partner,
                        version: version,
                        requestBody: requestBody,
                        signature: signature,
                        //publicKey: publicKey,
                        //privateKey: privateKey,
                        algorithm: algorithm
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        ////$("#sign").text(data[0]);
                        $("#sign").val(data[0]);
                        //$("#responseBody").val(data[1]);
                    },
                    error: function (data) {
                        alert("异常");
                    }
                });

            });

            $("#makeVerifySign").click(function () {
                var requestBody = $("#requestBody").val();
                var sign = $("#sign").val();
                var algorithm = null;
                var signature = null;
                if ($("#algorithm1").is(':checked')) {
                    algorithm = 1;
                    signature = $("#signature").val();//sha256 sign
                }

                var url = "${ctx}" + "/mock/verifySign";
                $.ajax({
                    url: url,
                    type: 'post',
                    data:{
                        requestBody: requestBody,
                        sign: sign,
                        algorithm: algorithm,
                        signature: signature
                    },
                    dataType: 'json',
                    success: function (data) {
                        $('#responseBody').val(JSON.stringify(data));
                        ////$('#responseBody').val(JSON.stringify(data.responseBody));
                    },
                    error: function (data) {
                        alert("异常");
                    }
                });
            });

            $("#submit").click(function () {
                var signature = null; //MD5 Key
                var sign = null; //MD5或者RSA的签名sign值

                var algorithm = null;
                if ($("#algorithm1").is(':checked')) {
                    algorithm = 1;
                    signature = $("#signature").val();//sha256 sign
                }
                var url = "${ctx}" + "/mock/doMock";
                var requestBody = $("#requestBody").val();
                var partner = $("#partner").val();
                var version = $("#version").val();
                //var publicKey = $("#publicKey").val();
                //var privateKey = $("#privateKey").val();
                var sign = $("#sign").val();
                $.ajax({
                    url: url,
                    data: {
                        partner: partner,
                        version: version,
                        requestBody: requestBody,
                        //publicKey: publicKey,
                        //privateKey: privateKey,
                        algorithm: algorithm,
                        signature: signature,
                        sign: sign
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        $("#sign").val(data.sign);
                        $('#responseBody').val(data.responseBody);
                        ////$('#responseBody').val(JSON.stringify(data.responseBody));
                    },
                    error: function (data) {
                        alert("异常");
                    }
                });
            });
        })
    </script>
</head>
<body>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">申请网关交互</h1>
        </div>
    </div>
    <form class="form-horizontal" id="baseForm" action="${ctx}/mock/doMock" role="form" method="post">
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        请选择一种算法
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="form-group has-success has-feedback">
                                    <label class="control-label col-lg-2" for="algorithm1">请选择算法</label>
                                    <label class="radio-inline">
                                        <input type="radio" name="algorithm" id="algorithm1" value="1">SHA256
                                    </label>
                                </div>

                                <div class="form-group has-success has-feedback" id="sha256">
                                    <label class="control-label col-lg-2" for="signature">SHA256签名</label>

                                    <div class="col-lg-10">
                                        <input class="form-control validate[required]" id="signature" name="signature"
                                               type="text"/>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                       请填下header部分信息
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group has-success has-feedback">
                                    <label class="control-label col-lg-3" for="partner">合伙人</label>

                                    <div class="col-lg-9">
                                        <input class="form-control validate[required,custom[integer]]" id="partner"
                                               name="partner" type="text"/>
                                    </div>
                                </div>
                                <div class="form-group has-success has-feedback">
                                    <label class="control-label col-lg-3" for="requestBody">请求体信息</label>

                                    <div class="col-lg-9">
                                        <textarea class="form-control validate[required]" id="requestBody"
                                                  name="requestBody" rows="8"></textarea>
                                    </div>
                                </div>
                                <div class="form-group has-success has-feedback">
                                    <label class="control-label col-lg-3" for="version">版本</label>

                                    <div class="col-lg-9">
                                        <select class="form-control validate[required]" id="version" name="version">
                                            <option value="v1.0">v1.0</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group has-success has-feedback">
                                    <label class="control-label col-lg-3" for="sign">签名</label>

                                    <div class="col-lg-9">
                                        <textarea class="form-control validate[required]" id="sign" name="sign" rows="6"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <button type="button" class="btn btn-success" id="submit">提交</button>
        <button type="reset" class="btn btn-danger">重置</button>&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-warning" id="makeSign">签名</button>
        <button type="button" class="btn btn-warning" id="makeVerifySign">验签</button>

        <div class="row mTop20">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        响应结果如下
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="form-group has-success has-feedback">
                                    <label class="control-label col-lg-1" for="requestBody">响应结果</label>

                                    <div class="col-lg-11">
                                        <textarea class="form-control validate[required]" id="responseBody"
                                                  name="responseBody" rows="10"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
