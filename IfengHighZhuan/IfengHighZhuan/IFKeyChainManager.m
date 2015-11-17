//
//  IFKeyChainManager.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "IFKeyChainManager.h"
#import "IFengKeyChain.h"

static NSString * const KEY_IN_KEYCHAIN = @"com.ifeng.highzhuan.key";
static NSString * const KEY_PASSWORD = @"com.ifeng.highzhuan.password";

@implementation IFKeyChainManager
+(void)savePassWord:(NSString *)password
{
    NSMutableDictionary *usernamepasswordKVPairs = [NSMutableDictionary dictionary];
    [usernamepasswordKVPairs setObject:password forKey:KEY_PASSWORD];
    [IFengKeyChain save:KEY_IN_KEYCHAIN data:usernamepasswordKVPairs];
}
+(void)saveUserInfo:(NSDictionary *)userInfo {
    [IFengKeyChain save:KEY_IN_KEYCHAIN data:userInfo];
}
+(id)readUserInfo {
    NSMutableDictionary *usernamepasswordKVPair = (NSMutableDictionary *)[IFengKeyChain load:KEY_IN_KEYCHAIN];
    return usernamepasswordKVPair;

}
+(id)readPassWord
{
    NSMutableDictionary *usernamepasswordKVPair = (NSMutableDictionary *)[IFengKeyChain load:KEY_IN_KEYCHAIN];
    return [usernamepasswordKVPair objectForKey:KEY_PASSWORD];
}
+(void)deletePassword{
       NSMutableDictionary *usernamepasswordKVPair = (NSMutableDictionary *)[IFengKeyChain load:KEY_IN_KEYCHAIN];
    [usernamepasswordKVPair removeObjectForKey:KEY_IN_KEYCHAIN];


}
@end
