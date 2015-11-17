//
//  GuideViewController.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "GuideViewController.h"
#import "IFKeyChainManager.h"
#import "IfengHigh.h"
#import "AFAppDotNetAPIClient.h"
#import "IFUserInfoModel.h"
#import "ViewController.h"

@interface GuideViewController()<UIAlertViewDelegate>{

    CGRect originRect;

}
@property (nonatomic, assign) BOOL isPopKeyboard;
@property (nonatomic, assign) int hideHeight;
@property (nonatomic, assign) CGRect originRect;

@end

@implementation GuideViewController

-(IBAction)sendUserIdReq:(id)sender {
    [self.userIdTextF resignFirstResponder];
    
    if (self.userIdTextF.text.length > 0) {
        [MBProgressHUD showHUDAddedTo:self.view animated:YES];
        [IFUserInfoModel getUserInfo:^(BOOL result, NSDictionary *userList, NSString *failedMessage, NSError *error) {
            self.sureBtn.userInteractionEnabled = YES;
            if (result) {
                [IFKeyChainManager saveUserInfo:userList];
                [ViewController pushActiveListViewController:self.navigationController dict:userList];
                
            }else{
                
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:failedMessage message:nil delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
                [alert show];
          
              
            }
            
            [MBProgressHUD hideHUDForView:self.view animated:YES];
            
        } andUserId:self.userIdTextF.text];
    }else {
        UIAlertView *alertV = [[UIAlertView alloc] initWithTitle:@"请输入激活码" message:nil delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
        [alertV show];
        
    
    }
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 0) {
        [self.userIdTextF becomeFirstResponder];
    }



}

-(void)viewDidLoad{
    [super viewDidLoad];
    self.navigationController.navigationBarHidden = YES;
    CGRect rect = self.userIdTextF.frame;
    rect.size.height = 40;
    self.userIdTextF.frame = rect;
    originRect = self.view.frame;
    [self.userIdTextF setValue:[UIColor colorWithRed:(float)21/255 green:(float)170/255 blue:(float)247/255 alpha:1.0] forKeyPath:@"_placeholderLabel.textColor"];
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillChangeFrame:) name:UIKeyboardWillChangeFrameNotification object:nil];
//    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardWillHideFrame:) name:UIKeyboardWillHideNotification object:nil];
  
    //[self layoutNavBar];
}

-(void)layoutNavBar{
    UINavigationBar *pbNaviagationBar = self.navigationController.navigationBar;
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    [button setTitle:@"确定" forState:UIControlStateNormal];
    [button setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
    [button addTarget:self action:@selector(sendUserIdReq:)
     forControlEvents:UIControlEventTouchUpInside];
    CGRect rect = pbNaviagationBar.frame;
    button.frame = CGRectMake(0, (rect.size.height-20)/2, 40, 20);
    
    UIBarButtonItem *menuButton = [[UIBarButtonItem alloc] initWithCustomView:button];
    self.navigationItem.rightBarButtonItem = menuButton;
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 160, 25)];
    label.backgroundColor = [UIColor clearColor];
    label.textColor = [UIColor redColor];
    label.font = [UIFont boldSystemFontOfSize:18];
    label.textAlignment = NSTextAlignmentCenter;
    label.text = @"邀请码";
    self.navigationItem.titleView = label;


}


#pragma mark KEYBOARD
- (void)keyboardWillChangeFrame:(NSNotification*)notif{
    NSValue *keyboardBoundsValue = [[notif userInfo] objectForKey:UIKeyboardFrameEndUserInfoKey];
    CGRect keyboardEndRect = [keyboardBoundsValue CGRectValue];
    if(!self.isPopKeyboard || (keyboardEndRect.size.height!=0&&self.hideHeight!=keyboardEndRect.size.height)){
        self.hideHeight = keyboardEndRect.size.height;
        [self changeScrollHeight:self.hideHeight];
        self.isPopKeyboard = TRUE;
    }
}

- (void)keyboardWillHideFrame:(NSNotification*)notif{
    [self changeScrollHeight:0];
    self.userIdTextF = nil;
    self.isPopKeyboard = FALSE;
}

- (void)changeScrollHeight:(int)offH{
    if(!self.isPopKeyboard){
        self.originRect = self.view.frame;
        
    }
    CGRect rect = self.view.frame;
    rect.size.height = self.originRect.size.height-offH;
    self.view.frame = rect;
    UIView* parentView = self.userIdTextF.superview;
    int offY = parentView.frame.origin.y+self.userIdTextF.superview.frame.origin.y
    +self.userIdTextF.superview.frame.size.height-rect.size.height;
    if(offY<0 || offH==0){
        offY = 0;
    }
}


#pragma mark textFieldDelegate

-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField {
    [UIView beginAnimations:@"Animation" context:nil];
    //设置动画的间隔时间
    [UIView setAnimationDuration:0.20];
    //??使用当前正在运行的状态开始下一段动画
    [UIView setAnimationBeginsFromCurrentState: YES];
    //设置视图移动的位移
    self.view.frame = CGRectMake(self.view.frame.origin.x, self.view.frame.origin.y - 100, self.view.frame.size.width, self.view.frame.size.height);
    //设置动画结束
    [UIView commitAnimations];
//
    return YES;
}

- (void)textFieldDidEndEditing:(UITextField *)textField{

    [UIView beginAnimations:@"Animation" context:nil];
    //设置动画的间隔时间
    [UIView setAnimationDuration:0.20];
    //??使用当前正在运行的状态开始下一段动画
    [UIView setAnimationBeginsFromCurrentState: YES];
    //设置视图移动的位移
    self.view.frame = originRect;
    //设置动画结束
    [UIView commitAnimations];

}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
  //  [textField resignFirstResponder];
    [self sendUserIdReq:nil];
    return YES;

}
@end
