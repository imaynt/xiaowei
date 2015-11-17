//
//  IFVersionItem.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/11/3.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "IFVersionItem.h"

@implementation IFVersionItem
-(id)initWithDic:(NSDictionary *)attri{
    self = [super init];
    if (self) {
        self.vrOfficialid = [attri valueForKey:@"officialid"];
        self.vrMinVersion = [attri valueForKey:@"minVersion"];
        self.vrUrl = [attri valueForKey:@"url"];
        self.vrVersionid = [attri valueForKey:@"versionid"];
        self.vrexplanation = [attri valueForKey:@"explanation"];
    }
    return self;
}
@end
