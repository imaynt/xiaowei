//
//  IFUserInfoModel.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AFNetworking.h"
#import "IFVersionItem.h"


@interface IFUserInfoModel : NSObject

@property (nonatomic,retain)NSMutableArray *listArray;

+(NSURLSessionTask *)getUserInfo:(void (^)(BOOL result,NSDictionary *userList,NSString *failedMessage, NSError *error))block andUserId:(NSString *)userId;
+(NSURLSessionTask *)getAllTaskList:(void (^)(BOOL result,NSArray *listArray,NSString *failedMessage,NSError *error))block;
+(NSURLSessionTask *)notifyTaskComplete:(void (^)(BOOL result,NSString *failedMessage,NSError *error))block andUserId:(NSString *)userId appId:(NSString *)appId price:(NSString *)price;
+(NSURLSessionTask *)channelListTask:(void (^)(BOOL result,NSArray *listArray,NSString *exchange,NSString *failedMessage,NSError *error))block ;
+(NSURLSessionTask *)getVersionInfo:(void (^)(BOOL result,IFVersionItem *item,NSString *failedMessage,NSError *error))block;

@end
