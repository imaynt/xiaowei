//
//  ThirdCoorTaskViewController.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/19.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ThirdCoorTaskViewController : UIViewController<UITableViewDataSource,UITableViewDelegate>


@property (nonatomic ,retain)IBOutlet UITableView *tableView;
@property (nonatomic ,retain)IBOutlet UIView *introView;
@property (nonatomic ,retain)IBOutlet UILabel *exchangeL;
@property (nonatomic ,retain)IBOutlet UIButton *reloadBtn;
@property (nonatomic ,retain)IBOutlet UIImageView *reloadImv;
@property (nonatomic ,retain)IBOutlet UIView *reloadView;

-(IBAction)reloadAction:(id)sender;
@end
