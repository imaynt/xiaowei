//
//  IFKeyChainManager.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface IFKeyChainManager : NSObject
+(void)savePassWord:(NSString *)password;
+(void)saveUserInfo:(NSDictionary *)userInfo;
+(id)readPassWord;
+(id)readUserInfo;
+(void)deletePassword;
@end
