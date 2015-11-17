//
//  IFUserItem.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/15.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "IFUserItem.h"

@implementation IFUserItem
-(id)initWithDic:(NSDictionary *)attri{
    self = [super init];
    if (self) {
        self.appId = [attri valueForKey:@"appid"];
        self.package = [attri valueForKey:@"package"];
        self.price = [attri valueForKey:@"price"];
        self.liveTime = [attri valueForKey:@"live_time"];
        self.count = 0;
    }
    return self;
}

@end
