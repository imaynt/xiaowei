//
//  ViewController.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "ViewController.h"
#import "ThirdCoorTaskViewController.h"
#import "IFKeyChainManager.h"
#import "ProductSnake.h"
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
#import "MBProgressHUD.h"


@interface ViewController ()<YQLDelegate,JOJOWangSDKDelegate,CreativeHomeManagerDelegate,QMRecommendAppDelegate,MiidiMobAcSlabDelegate,UIAlertViewDelegate>{

    CreativeHomeManager*_manager;
    ADJUZWallSDK *juZahng;
    IFVersionItem *vrItem;
}
@property(nonatomic,strong)QumiOperationApp *qumiViewController;
@property (nonatomic,retain)NSDictionary *userDict;
@end
#define DR_APPKEY @"00009330220000BF"
@implementation ViewController



+(void)pushActiveListViewController:(UINavigationController *)navigitonController dict:(NSDictionary*)dict{
    if (!navigitonController) {
        return;
    }
    
    UIStoryboard *story = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    //由storyboard根据myView的storyBoardID来获取我们要切换的视图
    ViewController *rootViewController = [story instantiateViewControllerWithIdentifier:@"ViewController"];
    rootViewController.userDict = dict;
    [navigitonController pushViewController:rootViewController animated:YES];
}
- (void)viewDidLoad {
    [super viewDidLoad];
    [self layoutNavBar];
    [self getVersionInfo];

    [self initView];
    if (self.userDict == nil) {
        self.userDict = [IFKeyChainManager readUserInfo];
        [self sendUserIdReq];
    }
    [self updateInfo:self.userDict];
   // [self initThirdTaskList:[self.userDict valueForKey:@"userid"]];
  
}

-(void)getVersionInfo{
    NSDictionary *infoDictionary = [[NSBundle mainBundle] infoDictionary];
    NSString *ver = [infoDictionary objectForKey:@"CFBundleShortVersionString"];

    float app_Version = ver.floatValue;
    [IFUserInfoModel getVersionInfo:^(BOOL result, IFVersionItem *item, NSString *failedMessage, NSError *error) {
        if(result){
            vrItem = item;
            if (app_Version >= [item.vrMinVersion floatValue] && app_Version < [item.vrOfficialid floatValue]) {
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"版本更新" message:item.vrexplanation delegate:self cancelButtonTitle:@"暂不更新" otherButtonTitles:@"立即更新", nil];
                alert.tag = 99;
                [alert show];
            }else if(app_Version < [item.vrMinVersion floatValue]){
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"版本更新" message:item.vrexplanation delegate:self cancelButtonTitle:@"立即更新" otherButtonTitles:nil, nil];
                alert.tag = 100;
                [alert show];
            }
        }else {
            
        }
        
    }];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    
    if (alertView.tag == 99) {
        if (buttonIndex == 1) {
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:vrItem.vrUrl]];
        }
    }else if(alertView.tag == 100){
        if (buttonIndex == 0) {
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:vrItem.vrUrl]];
            
        }
        exit(-1);
        
    }
}

#pragma mark 跳转第三方页面
//-(void)toThirdView:(NSString *)str {
//    if ([str rangeOfString:@"1"].length > 0) {
//        [JOJOWangSDK showJOJOWangDiamondWithViewController:self];
//    }
//}

-(void)sendUserIdReq{
   // [MBProgressHUD showHUDAddedTo:self.view animated:YES];
        [IFUserInfoModel getUserInfo:^(BOOL result, NSDictionary *userList, NSString *failedMessage, NSError *error) {
            if (result) {
                if (userList) {
                     [self updateInfo:self.userDict];
                }
                [IFKeyChainManager saveUserInfo:userList];
              
            }else{
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"请检查您的网络" message:nil delegate:nil cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
                [alert show];
                
            }
            
           // [MBProgressHUD hideHUDForView:self.view animated:YES];
            
        } andUserId:[self.userDict valueForKey:@"userid"]];
    
}

#pragma mark -集成第三方-
/*-(void)initThirdTaskList:(NSString *)userId{
    //点入
    DR_INIT(DR_APPKEY, NO, userId);
    //点乐
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(offersWallConnectSuccess:) name:JOJOWang_CONNECT_SUCCESS_NOTIFICATION object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(offersWallConnectFailed:) name:JOJOWang_CONNECT_FAILED_NOTIFICATION object:nil];
    [JOJOWangSDK requestJOJOWangSession:@"ceada19f1571ed4c88d19f16149921f9" withUserID:userId];
    //趣米
    [QumiConfigTool startWithAPPID:@"39fc5b522932bd39" secretKey:@"2a7efb293d1f7487" appChannel:0];
    //米笛
    [MiidiMobAc setMiidiSlabPublisher:@"22866" withMiidiSlabSecret:@"fm5rv983j0wcdyfm"];
    [MiidiMobAc setMiidiSlabUserParam:@"1234"];
    //有米
    [PublicCallConfig PublicCallLaunchWithAppID:@"10c8ae2dfb3c077e" PublicCallAppSecret:@"77f92fe42cdb79f5"];
    [PublicCallConfig PublicCallSetUserID:userId];
    
    // [PublicCallPointsManager PublicCallEnable];
    //万普
    [AppConnect getConnect:@"964f6d45a717acce65dc8652d53b98f4" pid:nil userID:userId];
    _manager = [[CreativeHomeManager alloc] initWithPublisherID:@"96ZJ00WAzfogHwTOVj" andUserID:userId];
    _manager.delegate = self;
    // !!!:重要：如果需要禁用应用内下载，请将此值设置为YES。    _qumiViewController.isHiddenStatusBar = NO;
    
    //自动领取积分 开启自动领取积分功能填写YES 关闭填写NO
    [_qumiViewController autoGetPoints:NO];
    
    _manager.disableStoreKit = YES;
    
    [JOJOWangSDK setDelegate:self];
    
    //趣米
    _qumiViewController = [[QumiOperationApp alloc] initwithPointUserID:userId] ;
    //设置代理
    _qumiViewController.delegate = self;
    
    //是否隐藏状态栏，如果为YES就隐藏  NO是显示
    
    //巨掌
    juZahng = [[ADJUZWallSDK alloc] initAppKey:@"77CEF7E2B2D94524" andAppUserId:userId];

    
}
*/
//-(void)initThirdTaskList{
//    DR_INIT(DR_APPKEY, NO, nil);
//    [JOJOWangSDK requestJOJOWangSession:@"ceada19f1571ed4c88d19f16149921f9" withUserID:@"12353"];
//  //  [JOJOWangSDK requestJOJOWangSession:@"ceada19f1571ed4c88d19f16149921f9" withUserID:@"12353"];
////    [JJSDK requestJJSession:@"ceada19f1571ed4c88d19f16149921f9" withUserID:@"4aed14ff25173932f34642b27007cb83"];
//  //  [JOJOWangSDK setDelegate:self];
////    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(offersWallConnectSuccess:) name:JJ_CONNECT_SUCCESS_NOTIFICATION object:nil];
////    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(offersWallConnectFailed:) name:JJ_CONNECT_FAILED_NOTIFICATION object:nil];
//
//}

-(void)initView{
    CGSize size = [[UIScreen mainScreen] bounds].size;
    CGPoint point = self.view.frame.origin;
    self.backGroundView.frame = CGRectMake(point.x, point.y, size.width, size.height);
    self.backGroundView.autoresizingMask = UIViewAutoresizingFlexibleLeftMargin;
    CGRect rect = self.headImv.frame;
    self.headImv.autoresizingMask = UIViewAutoresizingFlexibleRightMargin;
    self.headImv.layer.masksToBounds =YES;
    self.headImv.layer.cornerRadius = rect.size.width/2;
    CALayer * layer = [self.headImv layer];
    layer.borderColor = [[UIColor colorWithRed:(float)92/255 green:(float)196/255 blue:(float)249/255 alpha:1.0] CGColor];
    layer.borderWidth = 5.0f;
}

-(void)updateInfo:(NSDictionary *)dict{
   // NSDictionary *dict = noti.userInfo;
    NSURL *photourl = [NSURL URLWithString:[dict valueForKey:@"avatar"]];
    //url请求实在UI主线程中进行的
    UIImage *images = [UIImage imageWithData:[NSData dataWithContentsOfURL:photourl]];
    CGRect rect = self.headImv.frame;
    if (images) {
         self.headImv.image = images;
    }else {
        self.headImv.image = [UIImage imageNamed:@"defaulticon"];
    }
   
    self.nameLabel.text = [dict valueForKey:@"nickname"];
    self.idLabel.text = [NSString stringWithFormat:@"ID:%@",[dict valueForKey:@"userid"]];
    self.headImv.layer.masksToBounds =YES;
    self.headImv.layer.cornerRadius = rect.size.width/2;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(IBAction)backToWeChat:(id)sender {
    NSURL * myURL_APP_A = [NSURL URLWithString:@"weixin://"];
    if ([[UIApplication sharedApplication] canOpenURL:myURL_APP_A]) {
        NSLog(@"canOpenURL");
        [[UIApplication sharedApplication] openURL:myURL_APP_A];
    }
}

#pragma mark - 进入第三方合作列表-
-(IBAction)goCoorTask:(id)sender {
    UIStoryboard *story = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
    //由storyboard根据myView的storyBoardID来获取我们要切换的视图
    ThirdCoorTaskViewController *thirdCoorTaskViewController = [story instantiateViewControllerWithIdentifier:@"ThirdCoorTaskViewController"];
    
    [self.navigationController pushViewController:thirdCoorTaskViewController animated:YES];
    self.goCoorBtn.userInteractionEnabled = NO;
}

-(void)viewWillAppear:(BOOL)animated{
    [super viewDidAppear:animated];
    self.navigationController.navigationBar.hidden = YES;
}
-(void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
  
    self.navigationController.navigationBar.hidden = NO;
    self.goCoorBtn.userInteractionEnabled = YES;
}
-(void)layoutNavBar{
   
//    UINavigationBar *pbNaviagationBar = self.navigationController.navigationBar;
//    [[self navigationItem] setHidesBackButton:YES];
//    
//    //    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
//    //    [button setTitle:@"确定" forState:UIControlStateNormal];
//    //    [button setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
//    //    [button addTarget:self action:@selector(sendUserIdReq:)
//    //     forControlEvents:UIControlEventTouchUpInside];
//    //    CGRect rect = pbNaviagationBar.frame;
//    //    button.frame = CGRectMake(0, (rect.size.height-20)/2, 40, 20);
//    //
//    //    UIBarButtonItem *menuButton = [[UIBarButtonItem alloc] initWithCustomView:button];
//    //    self.navigationItem.rightBarButtonItem = menuButton;
//    
//    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 160, 25)];
//    label.backgroundColor = [UIColor clearColor];
//    label.textColor = [UIColor redColor];
//    label.font = [UIFont boldSystemFontOfSize:18];
//    label.textAlignment = NSTextAlignmentCenter;
//    label.text = @"凤凰大兵";
//    self.navigationItem.titleView = label;
    
}

#pragma mark - DianJoy offers wall connect notifications
- (void)offersWallConnectSuccess:(NSNotification *)notification
{
    [JOJOWangSDK getUserJOJOWangPoints];
    //    NSLog(@"connection success");
    //    [alertView release];
}
- (void)offersWallConnectFailed:(NSNotification *)notification
{
    //    NSLog(@"connection failed");
}



@end
