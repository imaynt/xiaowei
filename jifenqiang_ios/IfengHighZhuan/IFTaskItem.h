//
//  IFTaskItem.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/21.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface IFTaskItem : NSObject
@property (nonatomic,retain)NSString *cpId;
@property (nonatomic,retain)NSString *cpName;
@property (nonatomic,retain)NSString *cpIcon;
@property (nonatomic,retain)NSString *introduce;
-(id)initWithDic:(NSDictionary *)attri;
@end
