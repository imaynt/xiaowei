//
//  IFTaskItem.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/21.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "IFTaskItem.h"

@implementation IFTaskItem

-(id)initWithDic:(NSDictionary *)attri{
    self = [super init];
    if (self) {
        self.cpId = [attri valueForKey:@"id"];
        self.cpName = [attri valueForKey:@"name"];
        self.cpIcon = [attri valueForKey:@"icon"];
        self.introduce = [attri valueForKey:@"introduce"];
    }
    return self;
}

@end
