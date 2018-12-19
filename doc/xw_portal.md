
# XX项目接口文档 #

## 接口文档描述 ##

### 简要说明 ###
	本文档针是商户接入的协议说明。
	阅读对象: 合作商户的技术开发人员
	

### 商户配置 ###
	商户配置:
		商户号: 100000
		签名算法: SHA256
	
	联调环境秘钥:
		签名秘钥: pqbe211ctqfbqo8ks3p3o7b82p01dygd679xd6iwwi0kajcgvlwzyk3mgcm69w82
	
### 报文结构 ###

- 请求和应答报文均分为报文头和报文体两部分。
- 报文头以 http head 方式传递。
- 报文体通过 json 格式以 http body 方式传递。
- 同步应答在同一http连接中直接返回。
- 异步应答通过回调合作方提供的notifyUrl方式完成。
- 服务器采用UTF8编码
- **系统中统一以responseCode字段表示操作的结果。"000"表示处理成功，其他均表示异常。**
- **日期类型在本协议中采用长整型,其含义为自1970-01-01 00:00:00.000 到当前时刻的时间距离**
- **服务名称(serviceName)请参考附录服务名列表**s
- **sign,token两者不同时为空**

### 接入URL ###
	测试环境:
		公网网关测试页面:	 http://188.131.181.153:9090/test
		公网网关: http://188.131.181.153:9090/gw/interact
		网关域名: http://gw.freeexchange.cn/gw/interact
		演示地址: http://demo.freeexchange.cn/card/
		

### 报文头 ###
报文头以 http head 方式传递

|参数|描述|类型|必填|
| :------: | :------: | :------: |  :------: |
|partner|接入方ID|String|Y|
|version|协议版本号(当前使用v1.0)|String|Y|
|sign|签名(报文体全文签名，SHA256算法)|String|N|
|token|令牌|String|N|
	
	sign token 两者不可同时为空 


### 报文体 ###

报文体通过json格式以http body方式传递
不同业务报文的请求和应答请参见具体业务接口


## 发送验证码 ##
### 描述 ###
	发送验证码

### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(sendVerifyCode) | String | Y |
| mobileNo | 手机号 | String | Y |
| bizCode | 业务码 | String | Y|

#### 业务码 ####
|参数|描述|
| ------ | ------ |
| REGISTRATION | 注册 |
| LOGIN | 登录 |

### 请求报文示例 ###
	{
		"serviceName": "sendVerifyCode",
		"mobileNo": "18017237797",
		"bizCode": "REGISTRATION"
	}

### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |
| data | 业务数据 | JSON | Y |
| data.verifyCodeId | 验证码标识 | String | Y |

### 响应报文示例 ###
	{
	  "responseCode" : "000",
	  "responseMsg" : "成功",
	  "data" : {
	    "verifyCodeId" : "1544578912408"
	  }
	}


## 注册 ##
### 描述 ###
	用户注册
### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(signup) | String | Y |
| identityValue | 用户标识 | String | Y |
| verifyCodeId | 验证码标识 | String | Y|
| verifyCodeValue | 验证码值 | String | Y|


### 请求报文示例 ###
	{
	   "serviceName": "signup",
	   "identityValue":"18017237797",
	   "verifyCodeValue":"666666",
	   "verifyCodeId":"1544701538642"
	}

### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |
| data | 业务数据 | JSON | Y |
| data.openId | 统一会员标识 | String | Y |
| data.token | 会话标识 | String | Y |

### 响应报文示例 ###
	{
	  "responseCode" : "000",
	  "responseMsg" : "成功",
	  "data" : {
	    "openId" : "522862104441651200",
	    "token" : "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MjI4NjIxMDQ0NDE2NTEyMDAiLCJzdWIiOiJ7XCJpbWdVcmxcIjpcImh0dHA6Ly85NC4xOTEuMjcuMTMzL2NhcmQvc3RhdGljL21lZGlhL2F2YXRhci4zYzBiNjFhMC5qcGVnXCIsXCJuaWNrTmFtZVwiOlwiMTgwMTcyMzc3OTdcIixcIm9wZW5JZFwiOjUyMjg2MjEwNDQ0MTY1MTIwMH0iLCJpc3MiOiJGRSIsImlhdCI6MTU0NDcwMTY0MCwiZXhwIjoxNTQ1MzA2NDQwfQ.dgREBaeZzVCKbNl4QWqpoTRDW3_mpAkIpBrINzB_X3w"
	  }
	}


## 快捷登录 ##
### 描述 ###
	用户快捷登录

### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(login) | String | Y |
| identityValue | 用户标识 | String | Y |
| verifyCodeId | 验证码标识 | String | Y|
| verifyCodeValue | 验证码值 | String | Y|

### 请求报文示例 ###
	{
	   "serviceName": "login",
	   "identityValue":"18017237797",
	   "verifyCodeValue":"666666",
	   "verifyCodeId":"1544701538642"
	}


### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |
| data | 业务数据 | JSON | Y |
| data.openId | 统一会员标识 | String | Y |
| data.token | 会话标识 | String | Y |


### 响应报文示例 ###
	{
	  "responseCode" : "000",
	  "responseMsg" : "成功",
	  "data" : {
	    "openId" : "522862104441651200",
	    "token" : "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1MjI4NjIxMDQ0NDE2NTEyMDAiLCJzdWIiOiJ7XCJpbWdVcmxcIjpcImh0dHA6Ly85NC4xOTEuMjcuMTMzL2NhcmQvc3RhdGljL21lZGlhL2F2YXRhci4zYzBiNjFhMC5qcGVnXCIsXCJuaWNrTmFtZVwiOlwiMTgwMTcyMzc3OTdcIixcIm9wZW5JZFwiOjUyMjg2MjEwNDQ0MTY1MTIwMH0iLCJpc3MiOiJGRSIsImlhdCI6MTU0NDcwMTY0MCwiZXhwIjoxNTQ1MzA2NDQwfQ.dgREBaeZzVCKbNl4QWqpoTRDW3_mpAkIpBrINzB_X3w"
	  }
	}


## 注销 ##
### 描述 ###
	用户登入

### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(logout) | String | Y |

### 请求报文示例 ###
	{
	   "serviceName": "logout"
	}

### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |

### 响应报文示例 ###
	{
	  "responseCode" : "000",
	  "responseMsg" : "成功"
	}



## 会员信息概览 ##
### 描述 ###
	会员信息

### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(memberOverview) | String | Y |
### 请求报文示例 ###
	{
	   "serviceName": "memberOverview"
	}


### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |
| data | 业务数据 | JSON | Y |
| data.openId | 统一会员标识 | String | Y |
| data.partner | 合作商户 | String | Y |
| data.nickName | 昵称 | String | Y |
| data.imgUrl | 头像地址 | String | Y |

### 响应报文示例 ###
	{
	    "responseCode": "000",
	    "responseMsg": "成功",
	    "data": {
	        "partner": 100000,
	        "openId": 522862104441651200,
	        "nickName": "18017237797",
	        "imgUrl": "http://94.191.27.133/card/static/media/avatar.3c0b61a0.jpeg"
	    }
	}



## 账户信息概览##
### 描述 ###
	账户信息

### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(accountOverview) | String | Y |
### 请求报文示例 ###
	{
	   "serviceName": "accountOverview"
	}


### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |
| data | 业务数据 | JSON | Y |
| data.accounts | 账户列表 | List | Y |
| account.accountId | 账户标识 | String | Y |
| account.accountTypeId | 账户类型标识 | String | Y |
| account.accountName | 账户名称 | String | Y |
| account.balance | 账户余额 | String | Y |

### 响应报文示例 ###

	{
	    "responseCode": "000",
	    "responseMsg": "成功",
	    "data": [
	        {
	            "accountId": "1000000",
	            "accountTypeId": "1",
	            "accountName": "现金账户",
	            "balance": "0.00"
	        },
	        {
	            "accountId": "1000001",
	            "accountTypeId": "2",
	            "accountName": "积分账户",
	            "balance": "0.00"
	        }
	    ]
	}

## 卡包信息概览 ##
### 描述 ###
	卡包信息

### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(cardOverview) | String | Y |
### 请求报文示例 ###
	{
	   "serviceName": "cardOverview"
	}


### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |
| data | 业务数据 | JSON | Y |
| data.cardNum | 卡数量 | Long | Y |
| data.couponNum | 券数量 | Long | Y |

### 响应报文示例 ###
	{
	    "responseCode": "000",
	    "responseMsg": "成功",
	    "data": {
	        "cardNum": 1,
	        "couponNum": 2
	    }
	}

## 卡列表##
### 描述 ###
	卡列表

### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(cardList) | String | Y |
### 请求报文示例 ###
	{
	   "serviceName": "cardList"
	}


### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |
| data | 业务数据 | JSON | Y |
| data.cards | 卡列表 | JSON | Y |
| card.cardNo | 卡号 | List | Y |
| card.cardName | 卡名称 | List | Y |
| card.issuerName | 发行方名称 | List | Y |
| card.cardUrl | 卡图片链接 | String | Y |
| card.logoUrl | 卡LOGO链接 | String | Y |


### 响应报文示例 ###

	{
	    "responseCode": "000",
	    "responseMsg": "成功",
	    "data": [
	        {
	            "cardNo": "88888888888",
	            "cardName": "黄金会员卡",
	            "issuerName": "测试商户",
	            "cardUrl": "https://www.freeexchange.cn/assets/innisfree.jpeg",
	            "logoUrl": "https://www.freeexchange.cn/assets/innisfreeLogo.jpeg"
	        }
	    ]
	}


## 券列表##
### 描述 ###
	券列表

### 请求 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| serviceName | 服务名称(couponList) | String | Y |
### 请求报文示例 ###
	{
	   "serviceName": "couponList"
	}


### 响应 ###
|参数|描述|类型|必填|
| ------ | :------: | :------: |  :------: |
| responseCode | 应答码 | String | Y |
| responseMsg | 应答信息 | String | Y |
| data | 业务数据 | JSON | Y |
| data.couponsMap | 券列表 | JSON | Y |
| couponsMap.key |  券状态(HOLD 未使用,NON_HOLD 不可使用) | String | Y |
| coupon.couponNo | 券号 | String | Y |
| coupon.issuerName | 发行方名称 | String | Y |
| coupon.validateBeginDate | 有效期开始时点 | String | Y |
| coupon.validateEndDate | 有效期结束时点 | String | Y |
| coupon.amount | 券面值 | String | Y |
| coupon.state | 券状态(HOLD 未使用,WRITEOFF 已使用,EXPIRY 过期) | String | Y |


### 响应报文示例 ###
	{
	    "responseCode": "000",
	    "responseMsg": "成功",
	    "data": {
	        "WRITEOFF": [
	            {
	                "couponNo": "860635895108",
	                "validateBeginDate": "2018-12-16",
	                "validateEndDate": "2018-12-16",
	                "issuerName": "测试商户",
	                "amount": "6.00",
	                "state": "WRITEOFF"
	            }
	        ],
	        "HOLD": [
	            {
	                "couponNo": "369100896789",
	                "validateBeginDate": "2018-12-16",
	                "validateEndDate": "2018-12-16",
	                "issuerName": "测试商户",
	                "amount": "8.00",
	                "state": "HOLD"
	            }
	        ]
	    }
	}


==============================商户服务端接口=========================================



### 返回码  ###
|名称|编码|
| ------ | :------: |
|处理成功|000|
|系统异常|999|

### SHA256测试数据 ###
	明文: adhcdmcRFTGHJKOLPDCJDHJCNSDKCKD
	签名秘钥: pqbe211ctqfbqo8ks3p3o7b82p01dygd679xd6iwwi0kajcgvlwzyk3mgcm69w82
	签名算法： SHA256
	签名值: e81f06d9134e12e74f074b104951ff83310c30120ea6bf05105a9226a31989ab




