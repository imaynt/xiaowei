//
//  HttpManager.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "HttpManager.h"
#import "IfengHigh.h"

@interface HttpManager()

@property (nonatomic,retain) NSURL *url;

@end

@implementation HttpManager


static HttpManager *manager = nil;

+(HttpManager*)sharedManager{

    if (!manager) {
        manager = [[HttpManager alloc] init];
    }
    return manager;
}
//-(id)init{
//
//    self.url = [NSURL URLWithString:IFENG_URL];
//
//
//
//}



@end
