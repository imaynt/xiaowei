//
//  ThirdTaskCell.h
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/19.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "IFTaskItem.h"

@interface ThirdTaskCell : UITableViewCell

@property (nonatomic,retain)IBOutlet UILabel *titleLabel;
@property (nonatomic,retain)IBOutlet UILabel *introLabel;
@property (nonatomic,retain)IBOutlet UIImageView *headImage;
@property (nonatomic,retain)UIImage *image;
-(UIImage *)loadImage:(IFTaskItem *)item;
@end
