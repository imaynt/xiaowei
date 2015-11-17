//
//  IFVersionItem.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/11/3.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface IFVersionItem : NSObject
@property (nonatomic,retain)NSString *vrOfficialid;
@property (nonatomic,retain)NSString *vrMinVersion;
@property (nonatomic,retain)NSString *vrUrl;
@property (nonatomic,retain)NSString *vrVersionid;
@property (nonatomic,retain)NSString *vrexplanation;
-(id)initWithDic:(NSDictionary *)attri;
@end
