//
//  ADJUZWallSDK.h
//  ADJUZWallSDK
//
//  Created by zz on 15/9/24.
//  Copyright (c) 2015年 ADJUZ. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
@interface ADJUZWallSDK : NSObject<UIAlertViewDelegate>
//利用appid和Appuserid初始化管理类对象
- (id)initAppKey:(NSString *)appKey andAppUserId:(NSString *)appUaerId;

//获取积分墙
-(void)openListOfferWall:(UIViewController *)viewController;
//获得积分
-(NSString *)getSorceAndIsShow:(BOOL)isShow ;
//消费积分
-(BOOL)spendSorce:(int )sorce;
//关闭积分墙
-(void)closeWall;
@end

