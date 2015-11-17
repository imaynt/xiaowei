//
//  GuideViewController.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MBProgressHUD.h"

@interface GuideViewController : UIViewController<UITextFieldDelegate>

@property (nonatomic,retain)IBOutlet UITextField *userIdTextF;

@property (nonatomic,retain)IBOutlet UIButton *sureBtn;
-(IBAction)sendUserIdReq:(id)sender;
@end
