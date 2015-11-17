//
//  ThirdCoorTaskViewController.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/19.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "ThirdCoorTaskViewController.h"
#import "ThirdTaskCell.h"
#import "ProductSnake.h"
#import "JOJOWangSDK.h"
#import "CreativeHomeManager.h"
#import "QumiOperationApp.h"
#import <SystemConfiguration/CaptiveNetwork.h>
#import "IFUserInfoModel.h"
#import <WPLib/AppConnect.h>
#import "ADJUZWallSDK.h"
#import "IFKeyChainManager.h"
#import "IFTaskItem.h"
#import "MBProgressHUD.h"


@interface ThirdCoorTaskViewController ()<YQLDelegate,JOJOWangSDKDelegate,CreativeHomeManagerDelegate,QMRecommendAppDelegate,MiidiMobAcSlabDelegate,UIAlertViewDelegate> {
    
//    NSArray *titleArray;
//    NSArray *introArray;
//    NSArray *imageArray;
    CreativeHomeManager*_manager;
    ADJUZWallSDK *juZahng;
    
}
@property (nonatomic,retain) NSMutableArray *list;
@property (nonatomic,retain) NSString *exchange;
@property(nonatomic,strong)QumiOperationApp *qumiViewController;
@end

@implementation ThirdCoorTaskViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self layoutNavigationBar];
    [self initThirdC];
//    titleArray = [NSArray arrayWithObjects:@"点入",@"点乐",@"多盟",@"趣米",@"米迪",@"有米", @"万普",@"巨掌",nil];//
//    imageArray = [NSArray arrayWithObjects:@"dianru.png",@"dianle.png",@"duomeng.png",@"qumi.png",@"midi.png",@"youmi.png",@"wanpu.png",@"juzhang.png", nil];//
//    introArray = [NSArray arrayWithObjects:@"返现概率为百分之九十！",@"返现概率为百分之百！",@"返现概率为百分之百！",@"返现概率为百分之百！",@"返现概率为百分之百！",@"返现概率为百分之九十！",@"返现概率为百分之九十！", @"返现概率为百分之百！",nil];//
    self.list = [NSMutableArray arrayWithCapacity:0];
    self.tableView.hidden = YES;
    self.introView.hidden = YES;
  
    [self sendReq];
    
    self.tableView.tableFooterView = [[UIView alloc] initWithFrame:CGRectZero];
    // Do any additional setup after loading the view.
}

-(void)viewWillAppear:(BOOL)animated{

    self.navigationController.navigationBarHidden = NO;
    [super viewWillAppear:animated];
}

-(void)sendReq{
      [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    [IFUserInfoModel channelListTask:^(BOOL result, NSArray *listArray,NSString *exchange, NSString *failedMessage, NSError *error) {
        [MBProgressHUD hideHUDForView:self.view animated:YES];
        if (result) {
            self.reloadView.hidden = YES;
            self.reloadBtn.hidden = YES;
            self.reloadImv.hidden = YES;
            self.list = [NSMutableArray arrayWithArray:listArray];
            self.tableView.hidden = NO;
            self.introView.hidden = NO;
            self.exchangeL.text = [NSString stringWithFormat:@"%@金币 = 1元",exchange];
            [self.tableView reloadData];
            
          
        }else {
            self.reloadView.hidden = NO;
            self.reloadBtn.hidden = NO;
            self.reloadImv.hidden = NO;
//            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:failedMessage message:@"重新加载" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
//        
//            [alert show];
        }
        
    }];
}

//- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex{
//    if (buttonIndex == 1) {
//        [self sendReq];
//    }
//
//
//
//}
-(void)initThirdC{
    
    NSDictionary *userDict = [IFKeyChainManager readUserInfo];
    NSString *userId =[userDict valueForKey:@"userid"];
//多盟
    
   //未设置 id
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

-(IBAction)reloadAction:(id)sender {
    
     [self sendReq];
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(void)layoutNavigationBar{
    UINavigationBar *pbNaviagationBar = self.navigationController.navigationBar;
    [[self navigationItem] setHidesBackButton:YES];
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    //[button setTitle:@"确定" forState:UIControlStateNormal];
    [button setImage:[UIImage imageNamed:@"backBg"] forState:UIControlStateNormal];
    [button setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
    [button addTarget:self action:@selector(backAction)
     forControlEvents:UIControlEventTouchUpInside];
    CGRect rect = pbNaviagationBar.frame;
    button.frame = CGRectMake(0, (rect.size.height-20)/2, 40, 20);
    button.contentEdgeInsets = UIEdgeInsetsMake(0, -20, 0, 0);
    UIBarButtonItem *menuButton = [[UIBarButtonItem alloc] initWithCustomView:button];
    self.navigationItem.leftBarButtonItem = menuButton;
 

    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 160, 25)];
    label.backgroundColor = [UIColor clearColor];
    label.textColor = [UIColor whiteColor];
    label.font = [UIFont boldSystemFontOfSize:18];
    label.textAlignment = NSTextAlignmentCenter;
    label.text = @"联盟任务";
    self.navigationController.navigationBar.barTintColor = [UIColor colorWithRed:(float)21/255 green:(float)170/255 blue:(float)247/255 alpha:1.0];
    self.navigationItem.titleView = label;
}

-(void)backAction{

    [self.navigationController popViewControllerAnimated:YES];

}
#pragma mark - UITableViewDataDelegate-
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.list.count;
  //  return titleArray.count;
    
}
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    
    IFTaskItem *item = [self.list objectAtIndex:indexPath.row];
    ThirdTaskCell *cell = [self.tableView dequeueReusableCellWithIdentifier:@"ThirdTaskCell"];
    if(!cell){
        cell = [[[NSBundle mainBundle] loadNibNamed:@"ThirdTaskCell" owner:self options:nil] lastObject];
    }
    
    cell.titleLabel.text = item.cpName;//[titleArray objectAtIndex:indexPath.row];
    cell.headImage.image = [cell loadImage:item];//[UIImage imageNamed:[imageArray objectAtIndex:indexPath.row]];
    cell.introLabel.text = item.introduce;//[introArray objectAtIndex:indexPath.row];
    return cell;
    
}

-(UIImage *)loadImage:(IFTaskItem *)item{

    NSURL *photourl = [NSURL URLWithString:item.cpIcon];
    //url请求实在UI主线程中进行的
    UIImage *images = [UIImage imageWithData:[NSData dataWithContentsOfURL:photourl]];

    return images;


}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
     IFTaskItem *item = [self.list objectAtIndex:indexPath.row];
    
    switch ([item.cpId integerValue]) {
        case 1:
             [JOJOWangSDK showJOJOWangDiamondWithViewController:self];
            break;
        case 2:
             DR_SHOW(DR_OFFERWALL, self, self);
            break;
        case 3:
            [PublicCallList PublicCallShowOffers:YES PublicCallDidShowBlock:^{
                
            } PublicDidDismissBlock:^{
                
            }];
            break;
        case 4:
             [_manager presentCreativeHomeWithViewController:self type:eCreativeHomeTypeList];
            break;
        case 5:
             [AppConnect showList:self];
        case 6:
            [_qumiViewController presentQmRecommendApp:self];
            break;
        case 7:
             [MiidiMobAc showMiidiSlabView:self withMiidiDelegate:self];
            break;
        case 8:
            [juZahng openListOfferWall:self];
            break;
            
        default:
            break;
    }
    
//    if ([item.cpId integerValue] == 1) {
//        DR_SHOW(DR_OFFERWALL, self, self);
//    }else if ([item.cpId integerValue] == 2) {
//        [JOJOWangSDK showJOJOWangDiamondWithViewController:self];
//    }else if(indexPath.row == 2){
//    
//       [_manager presentCreativeHomeWithViewController:self type:eCreativeHomeTypeList];
//    }else if(indexPath.row == 3){
//    
//        [_qumiViewController presentQmRecommendApp:self];
//
//    }else if(indexPath.row == 4){
//    
//       [MiidiMobAc showMiidiSlabView:self withMiidiDelegate:self];
//    
//    }else if(indexPath.row == 5){
//        
//            [PublicCallList PublicCallShowOffers:YES PublicCallDidShowBlock:^{
//                NSLog(@"积分墙已经展示");
//            } PublicDidDismissBlock:^{
//                NSLog(@"积分墙退出");
//            }];
//    }else if(indexPath.row == 6){
//        [AppConnect showList:self];
//    
//    }else if(indexPath.row == 7){
//        //[juZahng openListOfferWall:self];
//    }
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath{
    if (indexPath.row>4) {
        cell.separatorInset = UIEdgeInsetsMake(10, 0, 0, 0);
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {

    return 61;

}

#pragma mark -点入回调-
- (void)dianruDidOpen:(UIViewController *)vc{
}

/*********************/
/*加载html失败         */
/*********************/
- (void)dianruDidLoadFail:(UIViewController *)vc {
    if (vc) {
        [vc dismissViewControllerAnimated:YES
                               completion:nil];
    }
}
- (void)dianruDidClose:(UIViewController *)vc{
    
}

/*********************/
/*从内存中销毁         */
/*********************/
- (void)dianruDidDestroy:(UIViewController *)vc{
    
}


#pragma mark - 点乐回调-
#pragma mark - DianJoySdkDelegate methods
- (void)getUserJJPointsFail:(NSError *)error
{
   // NSLog(@"getUserDianPointsFail");
}
- (void)getUserJJPointsSuccess:(int)dianPointsAmounts currency:(NSString *)currencyName
{
//    NSLog(@"getUserDianPointsSuccess");
//    NSString *labelString= [NSString stringWithFormat:@"%d%@",dianPointsAmounts,currencyName];
    //self.pointsLabel.text = labelString;
}
- (void)spendJJPointsSuccess:(int)dianPointsAmounts currency:(NSString *)currencyName
{
//    NSLog(@"spendDianPointsSuccess");
//    NSString *labelString= [NSString stringWithFormat:@"%d%@",dianPointsAmounts,currencyName];
//    // self.pointsLabel.text = labelString;
}
- (void)spendJJPointsFail:(NSError *)error
{
    //NSLog(@"spendDianPointsFail");
}
- (void)awardJJPointsFail:(NSError *)error
{
   // NSLog(@"awardDianPointsFail");
}
- (void)awardJJPointsSuccess:(int)dianPointsAmounts currency:(NSString *)currencyName
{
//    NSLog(@"awardDianPointsSuccess");
//    NSString *labelString= [NSString stringWithFormat:@"%d%@",dianPointsAmounts,currencyName];
    
    [JOJOWangSDK fetchParameterDefineByDeveloper:@""];
    // self.pointsLabel.text = labelString;
}

#pragma mark-多盟积分墙-
// 积分墙开始加载数据
// 积分墙开始加载数据
- (void)CreativeHomeManagerDidStartLoad:(CreativeHomeManager *)manager
                                   type:(CreativeHomeType)type {
    
    switch (type) {
            
        case eCreativeHomeTypeList:
            
            break;
        case eCreativeHomeTypeInterstitial:
            
            break;
        default:
            break;
    }
}

// 积分墙加载完成。
- (void)CreativeHomeManagerDidFinishLoad:(CreativeHomeManager *)manager
                                    type:(CreativeHomeType)type {
    
    switch (type) {
            
        case eCreativeHomeTypeList:
            
            break;
        case eCreativeHomeTypeInterstitial:
            
            break;
        default:
            break;
    }
}

// 积分墙加载失败。可能的原因由error部分提供，例如网络连接失败、被禁用等。
- (void)CreativeHomeManager:(CreativeHomeManager *)manager
        failedLoadWithError:(NSError *)error
                       type:(CreativeHomeType)type {
    switch (type) {
            
        case eCreativeHomeTypeList:
            
            break;
        case eCreativeHomeTypeInterstitial:
            
            break;
        default:
            break;
    }
}

// 当积分墙要被呈现出来时，回调该方法
- (void)CreativeHomeManagerWillPresent:(CreativeHomeManager *)manager
                                  type:(CreativeHomeType)type {
    
    switch (type) {
            
        case eCreativeHomeTypeList:
            
            break;
        case eCreativeHomeTypeInterstitial:
           
            break;
        default:
            break;
    }
}

//  积分墙页面关闭。
- (void)CreativeHomeManagerDidClosed:(CreativeHomeManager *)manager
                                type:(CreativeHomeType)type {
    
    switch (type) {
            
        case eCreativeHomeTypeList:
            
            break;
        case eCreativeHomeTypeInterstitial:
            
            break;
        default:
            break;
    }
}



#pragma mark  趣米 delegate
-(void)QMSuccessToLoaded:(QumiOperationApp *)qumiAdApp
{
 
}
-(void)QMFailToLoaded:(QumiOperationApp *)qumiAdApp withError:(NSError *)error
{
   
}
-(void)QMDismiss:(QumiOperationApp *)qumiAdApp
{
  
}

-(void)dealloc{
    
    _manager.delegate = nil;
    _manager= nil;
    _qumiViewController = nil;
    juZahng = nil;
    _list = nil;
    _exchange = nil;
    _reloadBtn = nil;
    _reloadImv = nil;
    _reloadView = nil;
    
}

/*
 #pragma mark - Navigation
 
 // In a storyboard-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
 // Get the new view controller using [segue destinationViewController].
 // Pass the selected object to the new view controller.
 }
 */

@end
