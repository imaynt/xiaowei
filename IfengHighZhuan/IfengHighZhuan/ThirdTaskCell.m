//
//  ThirdTaskCell.m
//  IfengHighZhuan
//
//  Created by 李胜男 on 15/10/19.
//  Copyright © 2015年 Linda Lee. All rights reserved.
//

#import "ThirdTaskCell.h"

@implementation ThirdTaskCell

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
-(UIImage *)loadImage:(IFTaskItem *)item{    
    NSURL *photourl = [NSURL URLWithString:item.cpIcon];
    //url请求实在UI主线程中进行的
    
    if (self.image == nil) {
        self.image = [UIImage imageWithData:[NSData dataWithContentsOfURL:photourl]];

    }
    if (self.image == nil) {
        
        self.image = [UIImage imageNamed:[NSString stringWithFormat:@"%@.png",item.cpId]];
    }

    
    return self.image;
//        
//        return images;
//    }
//    item.cpIcon.length>5?[UIImage imageWithData:[NSData dataWithContentsOfURL:photourl]]:[UIImage imageNamed:[NSString stringWithFormat:@"%@.png",item.cpId]];
    
    
}

@end
