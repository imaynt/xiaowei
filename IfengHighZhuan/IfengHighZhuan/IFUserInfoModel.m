//
//  IFUserInfoModel.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "IFUserInfoModel.h"
#import "AFAppDotNetAPIClient.h"
#import "IfengHigh.h"
#import "NSString+JSonString.h"
#import <AdSupport/AdSupport.h>
#import "MD5.h"
#import "IFUserItem.h"
#import "IFTaskItem.h"


@implementation IFUserInfoModel

+(NSURLSessionTask *)getUserInfo:(void (^)(BOOL result,NSDictionary *userList,NSString *failedMessage, NSError *error))block andUserId:(NSString *)userId{
    NSMutableDictionary * tempDict = [NSMutableDictionary dictionaryWithCapacity:0];
    [tempDict setObject:@"getUserInfo" forKey:@"fun"];
    [tempDict setObject:userId forKey:@"userid"];
    NSString *adId = [[[ASIdentifierManager sharedManager] advertisingIdentifier] UUIDString];
    [tempDict setObject:adId forKey:@"deviceid"];
    [tempDict setObject:@"ios" forKey:@"platform"];
    NSString *osVersion = [[UIDevice currentDevice] systemVersion];
    [tempDict setObject:osVersion forKey:@"oscode"];
    NSString *key = @"u9Y%)!a1z";
    NSMutableString *str = [NSMutableString stringWithFormat:@"userid=%@&deviceid=%@&platform=ios&oscode=%@||%@",userId,adId,osVersion,key];
    NSString *signStr = [MD5 md5:str];
    [tempDict setObject:signStr forKey:@"sign"];
    return [[AFAppDotNetAPIClient sharedClient] POST:AFAppDotNetAPIBaseURLString parameters:tempDict success:^(NSURLSessionDataTask * task, id responseObject) {
       // NSLog(@"responseObject = %@",responseObject);
//        NSString * response = [[NSString alloc]initWithData:responseObject encoding:NSUTF8StringEncoding];
//        NSDictionary * responseDict = [NSString dictionaryWithJsonString:response];
        NSDictionary * responseDict = (NSDictionary *)responseObject;
        NSDictionary *contentDic = [responseDict valueForKey:@"content"];
        BOOL result = [[responseDict objectForKey:@"result"]intValue];
        NSString* failedMessage = [responseDict objectForKey:@"msg"];
        if (block) {
            block(!result, contentDic,failedMessage, nil);
        }
    } failure:^(NSURLSessionDataTask * _Nonnull task, NSError * _Nonnull error) {
        if (block) {
            block(NO,nil,nil,error);
        }

    }];
    
    
}

+(NSURLSessionTask *)getAllTaskList:(void (^)(BOOL result,NSArray *listArray,NSString *failedMessage,NSError *error))block{
    NSMutableDictionary * tempDict = [NSMutableDictionary dictionaryWithCapacity:0];
    [tempDict setObject:@"appList" forKey:@"fun"];
    [tempDict setObject:@"0" forKey:@"pageindex"];
    [tempDict setObject:@"0" forKey:@"pagecount"];
    return [[AFAppDotNetAPIClient sharedClient] POST:AFAppDotNetAPIBaseURLString parameters:tempDict success:^(NSURLSessionDataTask * task, id responseObject) {
        NSDictionary * responseDict = (NSDictionary *)responseObject;
        /*NSString * response = [[NSString alloc]initWithData:responseObject encoding:NSUTF8StringEncoding];
       
        NSDictionary * responseDict = [NSString dictionaryWithJsonString:response];*/
        BOOL result = [[responseDict objectForKey:@"result"]intValue];
        NSString* failedMessage = [responseDict objectForKey:@"msg"];
        NSArray *list = [responseDict valueForKey:@"content"];
        NSMutableArray *lisArray = [NSMutableArray arrayWithCapacity:0];
        for (int i = 0; i < list.count; i++) {
            NSDictionary *dict = [list objectAtIndex:i];
            IFUserItem *item = [[IFUserItem alloc] initWithDic:dict];
            [lisArray addObject:item];
        }
        if (block) {
            block(!result, lisArray,failedMessage, nil);
        }
    } failure:^(NSURLSessionDataTask * _Nonnull task, NSError * _Nonnull error) {
        if (block) {
            block(NO,nil,nil,error);
        }
        
    }];

}
+(NSURLSessionTask *)notifyTaskComplete:(void (^)(BOOL result,NSString *failedMessage,NSError *error))block andUserId:(NSString *)userId appId:(NSString *)appId price:(NSString *)price{
    NSMutableDictionary * tempDict = [NSMutableDictionary dictionaryWithCapacity:0];
    [tempDict setObject:@"appRunNotify" forKey:@"fun"];
    [tempDict setObject:userId forKey:@"userid"];
    [tempDict setObject:appId forKey:@"appid"];
    [tempDict setObject:price forKey:@"price"];
    NSString *key = @"u9Y%)!a1z";
    NSMutableString *str = [NSMutableString stringWithFormat:@"userid=%@&appid=%@&price=%@||%@",userId,appId,price,key];
    NSString *signStr = [MD5 md5:str];
    [tempDict setObject:signStr forKey:@"sign"];
    return [[AFAppDotNetAPIClient sharedClient] POST:AFAppDotNetAPIBaseURLString parameters:tempDict success:^(NSURLSessionDataTask * task, id responseObject) {
       // NSString * response = [[NSString alloc]initWithData:responseObject encoding:NSUTF8StringEncoding];
        NSDictionary * responseDict = (NSDictionary *)responseObject;
       // NSDictionary * responseDict = [NSString dictionaryWithJsonString:response];
        BOOL result = [[responseDict objectForKey:@"result"]intValue];
        NSString* failedMessage = [responseDict objectForKey:@"msg"];
        if (block) {
            block(!result,failedMessage, nil);
        }
    } failure:^(NSURLSessionDataTask * _Nonnull task, NSError * _Nonnull error) {
        if (block) {
            block(NO,nil,error);
        }
        
    }];

}
+(NSURLSessionTask *)channelListTask:(void (^)(BOOL result,NSArray *listArray,NSString *exchange,NSString *failedMessage,NSError *error))block {
    NSMutableDictionary * tempDict = [NSMutableDictionary dictionaryWithCapacity:0];
    
    [tempDict setObject:@"channelList" forKey:@"fun"];
    [tempDict setObject:@"1" forKeyedSubscript:@"os"];
    return [[AFAppDotNetAPIClient sharedClient] POST:AFAppDotNetAPIBaseURLString parameters:tempDict success:^(NSURLSessionDataTask * task, id responseObject) {
        NSDictionary * responseDict = (NSDictionary *)responseObject;
       // NSLog(@"responseDict = %@",responseDict);
        BOOL result = [[responseDict objectForKey:@"result"]intValue];
        NSString* failedMessage = [responseDict objectForKey:@"msg"];
        NSArray *list = [responseDict valueForKey:@"content"];
        NSMutableArray *lisArray = [NSMutableArray arrayWithCapacity:0];
        NSString *exchange = [responseDict objectForKey:@"exchange"];
        for (int i = 0; i < list.count; i++) {
            NSDictionary *dict = [list objectAtIndex:i];
            IFTaskItem *item = [[IFTaskItem alloc] initWithDic:dict];
            [lisArray addObject:item];
        }
        if (block) {
            block(!result, lisArray,exchange,failedMessage, nil);
        }
    } failure:^(NSURLSessionDataTask * _Nonnull task, NSError * _Nonnull error) {
        if (block) {
            block(NO,nil,nil,nil,error);
        }
        
    }];

}

+(NSURLSessionTask *)getVersionInfo:(void (^)(BOOL result,IFVersionItem *item,NSString *failedMessage,NSError *error))block{
    NSMutableDictionary * tempDict = [NSMutableDictionary dictionaryWithCapacity:0];
    
    [tempDict setObject:@"versionInfo" forKey:@"fun"];
    [tempDict setObject:@"1" forKeyedSubscript:@"os"];
    return [[AFAppDotNetAPIClient sharedClient] POST:AFAppDotNetAPIBaseURLString parameters:tempDict success:^(NSURLSessionDataTask * task, id responseObject) {
        NSDictionary * responseDict = (NSDictionary *)responseObject;
        NSLog(@"responseDict = %@",responseDict);
        BOOL result = [[responseDict objectForKey:@"result"]intValue];
        NSString* failedMessage = [responseDict objectForKey:@"msg"];
        NSDictionary *dict = [responseDict valueForKey:@"content"];
        IFVersionItem *item = [[IFVersionItem alloc] initWithDic:dict];
        
        if (block) {
            block(!result, item,failedMessage, nil);
        }
    } failure:^(NSURLSessionDataTask * _Nonnull task, NSError * _Nonnull error) {
        if (block) {
            block(NO,nil,nil,error);
        }
        
    }];
}


@end
