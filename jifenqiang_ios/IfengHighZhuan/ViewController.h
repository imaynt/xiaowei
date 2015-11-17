//
//  ViewController.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/13.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface ViewController : UIViewController
@property (nonatomic,retain)IBOutlet UIImageView *headImv;
@property (nonatomic,retain)IBOutlet UIView *backGroundView;
@property (nonatomic,retain)IBOutlet UILabel *nameLabel;
@property (nonatomic,retain)IBOutlet UILabel *idLabel;
@property (nonatomic,retain)IBOutlet UIButton *weChatLogin;
@property (nonatomic,retain)IBOutlet UIButton *goCoorBtn;

-(IBAction)backToWeChat:(id)sender;
-(IBAction)goCoorTask:(id)sender;

+(void)pushActiveListViewController:(UINavigationController *)navigitonController dict:(NSDictionary*)dict;
-(void)toThirdView:(NSString *)str;

@end

