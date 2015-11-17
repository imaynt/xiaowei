//
//  AppDelegate.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "AppDelegate.h"
#import "UIDevice+ProcessesAdditions.h"
#import "GuideViewController.h"
#import "IFKeyChainManager.h"
#import "IFUserInfoModel.h"
#import "IFUserItem.h"
#import "ViewController.h"
#import "JOJOWangSDK.h"
#import "JOJOWangDiamondConstants.h"
#import "MiidiMobAc.h"
#import <WPLib/AppConnect.h>
#import "MBProgressHUD.h"
#import "IFUserInfoModel.h"
#import "ADJUZWallSDK.h"
#import "IFTaskItem.h"
#import "CreativeHomeManager.h"
#import "CreativeHomeManager.h"
#import "QumiConfigTool.h"
#import "QumiOperationApp.h"
#import "ProductSnake.h"



//#import "MiidiMobAcApiConfig.h"


@interface AppDelegate ()<YQLDelegate,JOJOWangSDKDelegate,CreativeHomeManagerDelegate,QMRecommendAppDelegate,MiidiMobAcSlabDelegate> {
    
    NSInteger justCount;
    NSString *processName;
    NSString *userId;
    
    CreativeHomeManager*_manager;
    ADJUZWallSDK *juZahng;
   
}
@property(nonatomic,strong)QumiOperationApp *qumiViewController;
@property (nonatomic, unsafe_unretained) UIBackgroundTaskIdentifier backgroundTaskIdentifier;
@property (nonatomic, strong) NSTimer *myTimer;
@property (nonatomic,retain) NSMutableArray *list;
@end

#define DR_APPKEY @"00009330220000BF"
#define ARRAY_SIZE(a) sizeof(a)/sizeof(a[0])

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    
    if ([self isJailBreak] || [self isJailBreakCy]) {
        exit(-1);
    }
    
    NSDictionary *dict = [IFKeyChainManager readUserInfo];
    userId = [dict valueForKey:@"userid"];
    [self initThirdTaskList:userId];
    
    if ([IFKeyChainManager readUserInfo]) {
        
        UIStoryboard *story = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
        //由storyboard根据myView的storyBoardID来获取我们要切换的视图
        UIViewController *rootViewController = [story instantiateViewControllerWithIdentifier:@"ViewController"];
        UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:rootViewController];
        self.window.rootViewController = nav;
    }else {
        UIStoryboard *story = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
        //由storyboard根据myView的storyBoardID来获取我们要切换的视图
        UIViewController *rootViewController = [story instantiateViewControllerWithIdentifier:@"GuideViewController"];
        UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:rootViewController];
        self.window.rootViewController = nav;
        
    }
   // [self getVersionInfo];
    [self.window makeKeyAndVisible];
    [PublicCallConfig PublicCallSetFullScreenWindow:self.window];
    // Override point for customization after application launch.
    return YES;
}



- (BOOL)application:(UIApplication *)application handleOpenURL:(NSURL *)url {
    //    if ([url.absoluteString rangeOfString:@"tencent1104063021"].length > 0) {
    //        return [TencentOAuth HandleOpenURL:url];
    //    }else if([url.absoluteString rangeOfString:@"wb2255178897"].length > 0){
    //        return [WeiboSDK handleOpenURL:url delegate:self];
    //    }
    
    return YES;
}

- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(nullable NSString *)sourceApplication annotation:(id)annotation{
    NSString *handleUrl = [url absoluteString];
    UIStoryboard *story = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    if ([handleUrl rangeOfString:@"highzhuan://"].length > 1) {
        UINavigationController *vc = (UINavigationController *)_window.rootViewController;
        if (vc == nil) {
            if ([IFKeyChainManager readUserInfo]) {
                
                
                //由storyboard根据myView的storyBoardID来获取我们要切换的视图
                ViewController *rootViewController = [story instantiateViewControllerWithIdentifier:@"ViewController"];
                UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:rootViewController];
                self.window.rootViewController = nav;
               
                [self toThirdView:handleUrl];
            
            }else{
                UIStoryboard *story = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
                //由storyboard根据myView的storyBoardID来获取我们要切换的视图
                UIViewController *rootViewController = [story instantiateViewControllerWithIdentifier:@"GuideViewController"];
                UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:rootViewController];
                self.window.rootViewController = nav;
                
            }
            
        }else {
            if ([IFKeyChainManager readUserInfo]){
                [self toThirdView:handleUrl];
            }else {
                
                UIStoryboard *story = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
                //由storyboard根据myView的storyBoardID来获取我们要切换的视图
                UIViewController *rootViewController = [story instantiateViewControllerWithIdentifier:@"GuideViewController"];
                UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:rootViewController];
                self.window.rootViewController = nav;
                
            }
            
            //            ViewController *rootViewController = [story instantiateViewControllerWithIdentifier:@"ViewController"];
            //              [ self.window.rootViewController toThirdView:handleUrl];
            
        }
        
        self.window.backgroundColor = [UIColor whiteColor];
        [self.window makeKeyAndVisible];
    }
    return YES;
    
}

#pragma mark -集成第三方-
-(void)initThirdTaskList:(NSString *)userI{
    
   // NSLog(@"================userI=============%@",userI);
    //点入
    DR_INIT(DR_APPKEY, NO, userId);
    //点乐
    //    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(offersWallConnectSuccess:) name:JOJOWang_CONNECT_SUCCESS_NOTIFICATION object:nil];
    //    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(offersWallConnectFailed:) name:JOJOWang_CONNECT_FAILED_NOTIFICATION object:nil];
     //ceada19f1571ed4c88d19f16149921f9
    [JOJOWangSDK requestJOJOWangSession:@"ceada19f1571ed4c88d19f16149921f9" withUserID:userI];
    [JOJOWangSDK setDelegate:self];
    //趣米
    [QumiConfigTool startWithAPPID:@"39fc5b522932bd39" secretKey:@"2a7efb293d1f7487" appChannel:0];
    //米笛
    [MiidiMobAc setMiidiSlabPublisher:@"22866" withMiidiSlabSecret:@"fm5rv983j0wcdyfm"];
    [MiidiMobAc setMiidiSlabUserParam:userI];
    //有米
    [PublicCallConfig PublicCallLaunchWithAppID:@"10c8ae2dfb3c077e" PublicCallAppSecret:@"77f92fe42cdb79f5"];
    [PublicCallConfig PublicCallSetUserID:userI];
    [PublicCallPointsManager PublicCallEnable];
    
    // [PublicCallPointsManager PublicCallEnable];
    //万普
    [AppConnect getConnect:@"964f6d45a717acce65dc8652d53b98f4" pid:@"AppStore" userID:userI];
    //多盟
    _manager = [[CreativeHomeManager alloc] initWithPublisherID:@"96ZJ00WAzfogHwTOVj" andUserID:userI];
    _manager.delegate = self;
    // !!!:重要：如果需要禁用应用内下载，请将此值设置为YES。    _qumiViewController.isHiddenStatusBar = NO;
    
    //自动领取积分 开启自动领取积分功能填写YES 关闭填写NO
    [_qumiViewController autoGetPoints:NO];
    
    _manager.disableStoreKit = YES;
    
    [JOJOWangSDK setDelegate:self];
    
    //趣米
    _qumiViewController = [[QumiOperationApp alloc] initwithPointUserID:userI] ;
    //设置代理
    _qumiViewController.delegate = self;
    
    //是否隐藏状态栏，如果为YES就隐藏  NO是显示
    
    //巨掌
    juZahng = [[ADJUZWallSDK alloc] initAppKey:@"77CEF7E2B2D94524" andAppUserId:userI];
    
    
}

#pragma mark 跳转第三方页面
-(void)toThirdView:(NSString *)str {
    if ([str rangeOfString:@"1"].length > 0) {
        [JOJOWangSDK showJOJOWangDiamondWithViewController:self.window.rootViewController];
    }else if([str rangeOfString:@"2"].length > 0){
        
        DR_SHOW(DR_OFFERWALL, self.window.rootViewController, self);
        
    }else if([str rangeOfString:@"3"].length > 0){
        
        [PublicCallList PublicCallShowOffers:YES PublicCallDidShowBlock:^{
            
        } PublicDidDismissBlock:^{
            
        }];
        
    }else if([str rangeOfString:@"4"].length > 0){
        
        [_manager presentCreativeHomeWithViewController:self.window.rootViewController type:eCreativeHomeTypeList];
        
    }else if([str rangeOfString:@"5"].length > 0){
        [AppConnect showList:self.window.rootViewController];
        
        
    }else if([str rangeOfString:@"6"].length > 0){
        [_qumiViewController presentQmRecommendApp:self.window.rootViewController];
        
        
    }else if([str rangeOfString:@"7"].length > 0){
        [MiidiMobAc showMiidiSlabView:self.window.rootViewController withMiidiDelegate:self];
        
        
    }else if([str rangeOfString:@"8"].length > 0){
        [juZahng openListOfferWall:self.window.rootViewController];
        
        
    }
    
    
    
}
#pragma mark -是否越狱-
- (BOOL)isJailBreak
{
    
    const char* jailbreak_tool_pathes[] = {
        "/Applications/Cydia.app",
        "/Library/MobileSubstrate/MobileSubstrate.dylib",
        "/bin/bash",
        "/usr/sbin/sshd",
        "/etc/apt"
    };
    for (int i=0; i<ARRAY_SIZE(jailbreak_tool_pathes); i++) {
        if ([[NSFileManager defaultManager] fileExistsAtPath:[NSString stringWithUTF8String:jailbreak_tool_pathes[i]]]) {
          //  NSLog(@"The device is jail broken!");
            return YES;
        }
    }
    
    return NO;
}

- (BOOL)isJailBreakCy
{
    if ([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"cydia://"]]) {
       // NSLog(@"The device is jail broken!");
        return YES;
    }
    NSLog(@"The device is NOT jail broken!");
    return NO;
}

-(void)getTaskList{
    
    [IFUserInfoModel getAllTaskList:^(BOOL result, NSArray *listArray, NSString *failedMessage, NSError *error) {
        if (result) {
            self.list = [NSMutableArray arrayWithArray:listArray];
            
        }else {
            
        }
        
    }];
}
- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    self.backgroundTaskIdentifier =[application beginBackgroundTaskWithExpirationHandler:^(void) {
        
        // [self endBackgroundTask];
        
    }];
    //    if (self.myTimer == nil) {
    //        [self initTimer];
    //    }
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    
    
}

#pragma 初始化计时器
-(void)initTimer{
    self.myTimer =[NSTimer scheduledTimerWithTimeInterval:30.0f
                   
                                                   target:self
                   
                                                 selector:@selector(judgeAppIsRunning)     userInfo:nil
                   
                                                  repeats:YES];
}

#pragma -mark 获取所有进程
-(void)judgeAppIsRunning{
    NSArray * processes = [[UIDevice currentDevice] runningProcesses];
    for (NSDictionary * dict in processes){
        NSInteger length = self.list.count;
        for (int i = 0; i < length; i++) {
            IFUserItem *item = [self.list objectAtIndex:i];
            NSString *currentName = item.package;
            NSInteger timerCount = [item.liveTime integerValue]/30;
            if ([currentName isEqualToString:[dict objectForKey:@"ProcessName"]]) {
                if (item.count == timerCount) {
                    [self completeTask:userId appId:item.appId price:item.price];
                    return;
                }else if(item.count < timerCount){
                    item.count++;
                }
                
            }
        }
        
        //  NSLog(@"%@ - %@", [dict objectForKey:@"ProcessID"], [dict objectForKey:@"ProcessName"]);
    }
    
}

-(void)completeTask:(NSString *)userI appId:(NSString *)appId price:(NSString *)price{
    [IFUserInfoModel notifyTaskComplete:^(BOOL result, NSString *failedMessage, NSError *error) {
        if (result) {
            for (int i = 0; i < self.list.count; i++) {
                IFUserItem *item = [self.list objectAtIndex:i];
                if ([item.appId isEqualToString:appId]) {
                    [self.list removeObjectAtIndex:i];
                }
            }
            
        }else{
            
            
        }
        
    } andUserId:userI appId:appId price:price];
    
    
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

-(void)dealloc{
    
    _qumiViewController = nil;
    _manager = nil;
    _qumiViewController = nil;
}

@end
