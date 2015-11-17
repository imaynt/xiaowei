//
//  IFUserItem.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/15.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface IFUserItem : NSObject
@property (nonatomic,retain)NSString *appId;
@property (nonatomic,retain)NSString *package;
@property (nonatomic,retain)NSString *price;
@property (nonatomic,retain)NSString *liveTime;
@property (nonatomic,assign)NSInteger count;
-(id)initWithDic:(NSDictionary *)attri;
@end
